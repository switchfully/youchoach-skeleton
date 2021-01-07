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
import com.switchfully.youcoach.security.authentication.jwt.JwtGenerator;
import com.switchfully.youcoach.security.authentication.user.api.Account;
import com.switchfully.youcoach.security.authentication.user.api.AccountService;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.switchfully.youcoach.security.authentication.user.Authority.ADMIN;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class ProfileService implements AccountService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    private final TopicRepository topicRepository;
    private JwtGenerator jwtGenerator;

    @Autowired
    public ProfileService(ProfileRepository profileRepository,
                          ProfileMapper profileMapper,
                          TopicRepository topicRepository,
                          JwtGenerator jwtGenerator
    ) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.topicRepository = topicRepository;
        this.jwtGenerator = jwtGenerator;
    }

    public ProfileDto updateProfile(long id, UpdateProfileDto updateProfileDto) {
        String oldEmail = getUserById(id).getEmail();
        if (!oldEmail.equalsIgnoreCase(updateProfileDto.getEmail()) && emailExists(updateProfileDto.getEmail())) {
            throw new IllegalStateException("Email already exists!");
        }

        Profile profile = assertUserExistsAndRetrieve(oldEmail);
        profile.setEmail(updateProfileDto.getEmail());
        profile.setFirstName(updateProfileDto.getFirstName());
        profile.setLastName(updateProfileDto.getLastName());
        profile.setClassYear(updateProfileDto.getClassYear());
        if (updateProfileDto.getYoucoachRole() != null) {
            profile.setRole(Role.valueOf(updateProfileDto.getYoucoachRole().getName()));
        }


        return new ProfileDto()
                .withEmail(updateProfileDto.getEmail())
                .withFirstName(updateProfileDto.getFirstName())
                .withLastName(updateProfileDto.getLastName())
                .withClassYear(updateProfileDto.getClassYear())
                .withYoucoachRole(new RoleDto(profile.getRole().name(), profile.getRole().getLabel()))
                .withToken(jwtGenerator.generateToken(profile));
    }

    public ProfileDto getUserById(long id) {
        return profileMapper.toCoacheeProfileDto(profileRepository.findById(id).orElseThrow(() -> new ProfileNotFoundException("Profile with id " + id + " not found.")));
    }

    public boolean emailExists(String email) {
        return profileRepository.existsByEmail(email);
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

    public CoachProfileDto getCoachProfile(Authentication principal, long id) {
        Profile coach = assertCoachExistsAndRetrieve(id);
        CoachProfileDto coachDto = profileMapper.toCoachProfileDto(coach);

        obliterateEmailForNonAdminsAndStrangers(principal, coachDto);

        return coachDto;
    }

    private void obliterateEmailForNonAdminsAndStrangers(Authentication principal, CoachProfileDto coach) {
        if (!principal.getAuthorities().contains(ADMIN) && !coach.getEmail().equals(principal.getName()))
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

    @Override
    public Optional<Profile> findByEmail(String userName) {
        return profileRepository.findByEmail(userName);
    }

    @Override
    public Account createAccount(CreateSecuredUserDto createSecuredUserDto) {
        Profile profile = profileMapper.toUser(createSecuredUserDto);
        return profileRepository.save(profile);
    }

    @Override
    public boolean existsByEmail(String email) {
        return profileRepository.existsByEmail(email);
    }
}
