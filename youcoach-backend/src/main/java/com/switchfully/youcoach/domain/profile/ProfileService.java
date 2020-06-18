package com.switchfully.youcoach.domain.profile;


import com.switchfully.youcoach.domain.coach.Coach;
import com.switchfully.youcoach.domain.coach.CoachRepository;
import com.switchfully.youcoach.domain.coach.api.CoachListingDto;
import com.switchfully.youcoach.security.verification.AccountVerificator;
import com.switchfully.youcoach.security.verification.VerificationService;
import com.switchfully.youcoach.domain.profile.api.ProfileUpdatedDto;
import com.switchfully.youcoach.domain.profile.api.ProfileDto;
import com.switchfully.youcoach.domain.profile.api.UpdateProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.security.verification.api.ValidateAccountDto;
import com.switchfully.youcoach.domain.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.verification.api.ResendVerificationDto;
import com.switchfully.youcoach.domain.coach.exception.CoachNotFoundException;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.domain.profile.api.ProfileMapper;
import com.switchfully.youcoach.domain.profile.exception.ProfileIdNotFoundException;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import com.switchfully.youcoach.security.verification.api.VerificationResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;

@Service
@Transactional
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final VerificationService verificationService;
    private final PasswordEncoder passwordEncoder;
    private final CoachRepository coachRepository;
    private final AccountVerificator accountVerificator;
    private final SecuredUserService securedUserService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, CoachRepository coachRepository, ProfileMapper profileMapper,
                          VerificationService verificationService, PasswordEncoder passwordEncoder,
                          AccountVerificator accountVerificator, SecuredUserService securedUserService) {
        this.profileRepository = profileRepository;
        this.coachRepository = coachRepository;
        this.profileMapper = profileMapper;
        this.verificationService = verificationService;
        this.passwordEncoder = passwordEncoder;
        this.accountVerificator = accountVerificator;
        this.securedUserService = securedUserService;
    }

    public SecuredUserDto createUser(CreateSecuredUserDto createSecuredUserDto) {
        performValidation(createSecuredUserDto);
        Profile newProfile = profileMapper.toUser(createSecuredUserDto);
        newProfile.setPassword(passwordEncoder.encode(newProfile.getPassword()));
        newProfile = profileRepository.save(newProfile);
        accountVerificator.createAccountVerification(newProfile);
        try {
            accountVerificator.sendVerificationEmail(newProfile);
        } catch (MessagingException ex){
            newProfile.setAccountEnabled(true);
            accountVerificator.removeAccountVerification(newProfile);
        }
        return profileMapper.toUserDto(newProfile);
    }

    public ProfileUpdatedDto updateProfile(String email, UpdateProfileDto updateProfileDto){
        performUpdateValidation(email, updateProfileDto);
        Profile profile = assertUserExistsAndRetrieve(email);
        profile.setEmail(updateProfileDto.getEmail());
        profile.setFirstName(updateProfileDto.getFirstName());
        profile.setLastName(updateProfileDto.getLastName());
        profile.setClassYear(updateProfileDto.getClassYear());
        profile.setPhotoUrl(updateProfileDto.getPhotoUrl());

        ProfileUpdatedDto cpu = (ProfileUpdatedDto) new ProfileUpdatedDto()
                .withEmail(updateProfileDto.getEmail())
                .withFirstName(updateProfileDto.getFirstName())
                .withLastName(updateProfileDto.getLastName())
                .withPhotoUrl(updateProfileDto.getPhotoUrl())
                .withClassYear(updateProfileDto.getClassYear());

        if(!email.equals(updateProfileDto.getEmail())) {
            cpu.setToken(securedUserService.generateAuthorizationBearerTokenForUser(updateProfileDto.getEmail()));
        }
        return cpu;
    }


    public SecuredUserDto getUserById(long id) {
        return profileMapper.toUserDto(profileRepository.findById(id).get());
    }

    public List<SecuredUserDto> getAllusers() {
        return profileMapper.toUserDto(profileRepository.findAll());
    }

    public boolean emailExists(String email){
        return profileRepository.existsByEmail(email);
    }

    private void performValidation(CreateSecuredUserDto createSecuredUserDto) {
        if (!verificationService.isEmailValid(createSecuredUserDto.getEmail())) {
            throw new IllegalStateException("Email is not valid !");
        } else if (emailExists(createSecuredUserDto.getEmail())) {
            throw new IllegalStateException("Email already exists!");
        } else if (!verificationService.isPasswordValid(createSecuredUserDto.getPassword())) {
            throw new IllegalStateException("Password needs te be 8 characters : --> 1 capital, 1 lowercase and 1 one number ");
        }
    }
    public void performUpdateValidation(String email, UpdateProfileDto updateProfileDto) {
        if (!email.equalsIgnoreCase(updateProfileDto.getEmail()) && emailExists(updateProfileDto.getEmail())) {
        throw new IllegalStateException("Email already exists!");
    }
}

    public ProfileDto getCoacheeProfile(long id){
        Profile profile = assertUserExistsAndRetrieve(id);
        return profileMapper.toCoacheeProfileDto(profile);
    }

    public ProfileDto getCoacheeProfile(String email){
        Profile profile = assertUserExistsAndRetrieve(email);
        return profileMapper.toCoacheeProfileDto(profile);
    }

    public CoachProfileDto getCoachProfileForUser(Profile profile){
        Coach coach = assertCoachExistsAndRetrieve(profile);
        return profileMapper.toCoachProfileDto(coach);
    }
    public CoachProfileDto getCoachProfileForUserWithEmail(String email){
        Coach coach = assertCoachExistsAndRetrieve(email);
        return profileMapper.toCoachProfileDto(coach);
    }

    public CoachProfileDto updateCoachInformation(String email, CoachProfileDto coachProfileDto){
        Coach coach = assertCoachExistsAndRetrieve(email);
        coach.setAvailability(coachProfileDto.getAvailability());
        coach.setIntroduction(coachProfileDto.getIntroduction());
        return coachProfileDto;
    }

    public Coach assertCoachExistsAndRetrieve(String email){
        return coachRepository.findCoachByProfile_Email(email).orElseThrow(CoachNotFoundException::new);
    }

    public CoachProfileDto getCoachProfile(Principal principal, long id){
        Coach coach = assertCoachExistsAndRetrieve(id);
        CoachProfileDto coachDto = profileMapper.toCoachProfileDto(coach);

        obliterateEmailForNonAdminsAndStrangers(principal, coachDto);

        return coachDto;
    }

    private void obliterateEmailForNonAdminsAndStrangers(Principal principal, CoachProfileDto coach) {
        if(!securedUserService.isAdmin(principal.getName()) && !coach.getEmail().equals(principal.getName())) coach.setEmail(null);
    }

    public CoachProfileDto getCoachProfile(String id){
        Coach coach = assertCoachExistsAndRetrieve(id);
        return profileMapper.toCoachProfileDto(coach);
    }

    public Coach assertCoachExistsAndRetrieve(long id){
        return coachRepository.findById(id).orElseThrow(CoachNotFoundException::new);
    }

    public Coach assertCoachExistsAndRetrieve(Profile profile){
        return coachRepository.findCoachByProfile(profile).orElseThrow(CoachNotFoundException::new);
    }

    private Profile assertUserExistsAndRetrieve(long id) {
        return profileRepository.findById(id).orElseThrow(ProfileIdNotFoundException::new);
    }
    private Profile assertUserExistsAndRetrieve(final String email){
        return profileRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public VerificationResultDto validateAccount(ValidateAccountDto validationData) {
        if(validationDataDoesNotMatch(validationData)) return new VerificationResultDto(false);

        accountVerificator.enableAccount(validationData.getEmail());
        return new VerificationResultDto(true);
    }

    private boolean validationDataDoesNotMatch(ValidateAccountDto validationData) {
        return !accountVerificator.doesVerificationCodeMatch(validationData.getVerificationCode(), validationData.getEmail());
    }

    public ResendVerificationDto resendValidation(ResendVerificationDto validationData){
        boolean result = accountVerificator.resendVerificationEmailFor(validationData.getEmail());
        validationData.setValidationBeenResend(result);
        return validationData;
    }


    public CoachListingDto getCoachProfiles() {
        List<Coach> coachList = coachRepository.findAll();

        CoachListingDto cl = profileMapper.toCoachListingDto(coachList);
        cl.getCoaches().forEach(coach -> coach.setEmail(null));
        return cl;
    }
}
