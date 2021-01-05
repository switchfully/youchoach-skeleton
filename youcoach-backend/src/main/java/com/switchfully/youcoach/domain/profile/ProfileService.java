package com.switchfully.youcoach.domain.profile;


import com.switchfully.youcoach.domain.profile.api.*;
import com.switchfully.youcoach.domain.profile.exception.ProfileIdNotFoundException;
import com.switchfully.youcoach.domain.profile.exception.ProfileNotFoundException;
import com.switchfully.youcoach.domain.profile.role.Role;
import com.switchfully.youcoach.domain.profile.role.coach.TopicRepository;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachListingDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachProfileDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachingTopicDto;
import com.switchfully.youcoach.domain.profile.role.coach.exception.CoachNotFoundException;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.security.verification.AccountVerificator;
import com.switchfully.youcoach.security.verification.VerificationService;
import com.switchfully.youcoach.security.verification.api.ResendVerificationDto;
import com.switchfully.youcoach.security.verification.api.ValidateAccountDto;
import com.switchfully.youcoach.security.verification.api.VerificationResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final VerificationService verificationService;
    private final PasswordEncoder passwordEncoder;
    private final AccountVerificator accountVerificator;
    private final SecuredUserService securedUserService;
    private final TopicRepository topicRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository,
                          ProfileMapper profileMapper,
                          VerificationService verificationService,
                          PasswordEncoder passwordEncoder,
                          AccountVerificator accountVerificator,
                          SecuredUserService securedUserService,
                          TopicRepository topicRepository) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.verificationService = verificationService;
        this.passwordEncoder = passwordEncoder;
        this.accountVerificator = accountVerificator;
        this.securedUserService = securedUserService;
        this.topicRepository = topicRepository;
    }

    public SecuredUserDto createUser(CreateSecuredUserDto createSecuredUserDto) {
        performValidation(createSecuredUserDto);
        Profile newProfile = profileMapper.toUser(createSecuredUserDto);
        newProfile.setPassword(passwordEncoder.encode(newProfile.getPassword()));
        profileRepository.save(newProfile);

        accountVerificator.sendVerificationEmail(newProfile);

        return profileMapper.toUserDto(newProfile);
    }

    public ProfileUpdatedDto updateProfile(String email, UpdateProfileDto updateProfileDto) {
        performUpdateValidation(email, updateProfileDto);
        Profile profile = assertUserExistsAndRetrieve(email);
        profile.setEmail(updateProfileDto.getEmail());
        profile.setFirstName(updateProfileDto.getFirstName());
        profile.setLastName(updateProfileDto.getLastName());
        profile.setClassYear(updateProfileDto.getClassYear());
        if (updateProfileDto.getYoucoachRole() != null) {
            profile.setRole(Role.valueOf(updateProfileDto.getYoucoachRole().getName()));
        }

        ProfileUpdatedDto cpu = (ProfileUpdatedDto) new ProfileUpdatedDto()
                .withEmail(updateProfileDto.getEmail())
                .withFirstName(updateProfileDto.getFirstName())
                .withLastName(updateProfileDto.getLastName())
                .withClassYear(updateProfileDto.getClassYear())
                .withYoucoachRole(new RoleDto(profile.getRole().name(), profile.getRole().getLabel()));

        if (!email.equals(updateProfileDto.getEmail())) {
            cpu.setToken(securedUserService.generateToken(updateProfileDto.getEmail()));
        }
        return cpu;
    }

    public SecuredUserDto getUserById(long id) {
        return profileMapper.toUserDto(profileRepository.findById(id).get());
    }

    public boolean emailExists(String email) {
        return profileRepository.existsByEmail(email);
    }

    private void performValidation(CreateSecuredUserDto createSecuredUserDto) {
        if (!verificationService.isEmailValid(createSecuredUserDto.getEmail())) {
            throw new IllegalStateException("Email is not valid !");
        }
        if (emailExists(createSecuredUserDto.getEmail())) {
            throw new IllegalStateException("Email already exists!");
        }
        if (!verificationService.isPasswordValid(createSecuredUserDto.getPassword())) {
            throw new IllegalStateException("Password needs te be 8 characters : --> 1 capital, 1 lowercase and 1 one number ");
        }
    }

    public void performUpdateValidation(String email, UpdateProfileDto updateProfileDto) {
        if (!email.equalsIgnoreCase(updateProfileDto.getEmail()) && emailExists(updateProfileDto.getEmail())) {
            throw new IllegalStateException("Email already exists!");
        }
    }

    public ProfileDto getCoacheeProfile(long id) {
        Profile profile = assertUserExistsAndRetrieve(id);
        return profileMapper.toCoacheeProfileDto(profile);
    }

    public CoachProfileDto getCoachProfileForUser(Profile profile) {
        return profileMapper.toCoachProfileDto(profile);
    }

    public CoachProfileDto getCoachProfileForUserWithEmail(String email) {
        Profile coach = assertCoachExistsAndRetrieve(email);
        return profileMapper.toCoachProfileDto(coach);
    }

    public CoachProfileDto updateCoachInformation(String email, CoachProfileDto coachProfileDto) {
        Profile profile = assertCoachExistsAndRetrieve(email);
        profile.setIntroduction(coachProfileDto.getIntroduction());
        profile.setAvailability(coachProfileDto.getAvailability());
        return coachProfileDto;
    }

    public Profile assertCoachExistsAndRetrieve(String email) {
        return profileRepository.findByEmail(email).orElseThrow(CoachNotFoundException::new);
    }

    public CoachProfileDto getCoachProfile(Principal principal, long id) {
        Profile coach = assertCoachExistsAndRetrieve(id);
        CoachProfileDto coachDto = profileMapper.toCoachProfileDto(coach);

        obliterateEmailForNonAdminsAndStrangers(principal, coachDto);

        return coachDto;
    }

    private void obliterateEmailForNonAdminsAndStrangers(Principal principal, CoachProfileDto coach) {
        if (!securedUserService.isAdmin(principal.getName()) && !coach.getEmail().equals(principal.getName()))
            coach.setEmail(null);
    }

    public Profile assertCoachExistsAndRetrieve(long id) {
        return profileRepository.findById(id).orElseThrow(CoachNotFoundException::new);
    }

    private Profile assertUserExistsAndRetrieve(long id) {
        return profileRepository.findById(id).orElseThrow(ProfileIdNotFoundException::new);
    }

    private Profile assertUserExistsAndRetrieve(final String email) {
        return profileRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public VerificationResultDto validateAccount(ValidateAccountDto validationData) {
        Profile profile = profileRepository.findByEmail(validationData.getEmail()).orElseThrow(() -> new ProfileNotFoundException(""));
        return new VerificationResultDto(accountVerificator.enableAccount(validationData.getVerificationCode(), profile));
    }

    public ResendVerificationDto resendValidation(ResendVerificationDto validationData) {
        Optional<Profile> profile = profileRepository.findByEmail(validationData.getEmail());
        if(profile.isPresent()) {
            boolean result = accountVerificator.resendVerificationEmailFor(profile.get());
            validationData.setValidationBeenResend(result);
        } else {
            validationData.setValidationBeenResend(true);
        }
        return validationData;
    }

    public CoachListingDto getCoachProfiles() {
        List<Profile> coachList = profileRepository.findAll()
                .stream()
                .filter(Profile::canHostSession)
                .collect(toList());

        CoachListingDto cl = profileMapper.toCoachListingDto(coachList);
        cl.getCoaches().forEach(coach -> coach.setEmail(null));
        return cl;
    }

    public List<ProfileDto> getProfiles() {
        return profileRepository.findAll().stream().map(profileMapper::toCoacheeProfileDto).collect(Collectors.toList());
    }

    public void updateTopics(long id, List<CoachingTopicDto> topicDtos) {
        profileRepository.findById(id).ifPresent(profile -> profile.updateTopics(profileMapper.toTopic(topicDtos)));
    }

    public List<String> getAllTopics() {
        return topicRepository.getAllTopics();
    }

    public void deleteProfile(long id) {
        profileRepository.findById(id).ifPresentOrElse(profileRepository::delete, () -> {
            throw new ProfileIdNotFoundException();
        });
    }
}
