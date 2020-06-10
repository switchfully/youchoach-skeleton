package com.switchfully.youcoach.member;


import com.switchfully.youcoach.coach.Coach;
import com.switchfully.youcoach.coach.CoachRepository;
import com.switchfully.youcoach.coach.api.CoachListingDto;
import com.switchfully.youcoach.security.verification.AccountVerificator;
import com.switchfully.youcoach.security.verification.VerificationService;
import com.switchfully.youcoach.member.api.CoacheeMemberUpdatedDto;
import com.switchfully.youcoach.member.api.CoacheeProfileDto;
import com.switchfully.youcoach.member.api.UpdateMemberDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.security.verification.api.ValidateAccountDto;
import com.switchfully.youcoach.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.verification.api.ResendVerificationDto;
import com.switchfully.youcoach.coach.exception.CoachNotFoundException;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.member.api.MemberMapper;
import com.switchfully.youcoach.member.exception.MemberIdNotFoundException;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import com.switchfully.youcoach.security.verification.api.VerificationResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final VerificationService verificationService;
    private final PasswordEncoder passwordEncoder;
    private final CoachRepository coachRepository;
    private final AccountVerificator accountVerificator;
    private final SecuredUserService securedUserService;
    private final Environment environment;

    @Autowired
    public MemberService(MemberRepository memberRepository, CoachRepository coachRepository, MemberMapper memberMapper,
                         VerificationService verificationService, PasswordEncoder passwordEncoder,
                         AccountVerificator accountVerificator, SecuredUserService securedUserService, Environment environment) {
        this.memberRepository = memberRepository;
        this.coachRepository = coachRepository;
        this.memberMapper = memberMapper;
        this.verificationService = verificationService;
        this.passwordEncoder = passwordEncoder;
        this.accountVerificator = accountVerificator;
        this.securedUserService = securedUserService;
        this.environment = environment;
    }

    public SecuredUserDto createUser(CreateSecuredUserDto createSecuredUserDto) {
        performValidation(createSecuredUserDto);
        Member newMember = memberMapper.toUser(createSecuredUserDto);
        newMember.setPassword(passwordEncoder.encode(newMember.getPassword()));
        newMember = memberRepository.save(newMember);
        accountVerificator.createAccountVerification(newMember);
        try {
            accountVerificator.sendVerificationEmail(newMember);
        } catch (MessagingException ex){
            newMember.setAccountEnabled(true);
            accountVerificator.removeAccountVerification(newMember);
        }
        return memberMapper.toUserDto(newMember);
    }

    public CoacheeMemberUpdatedDto updateProfile(String email, UpdateMemberDto updateMemberDto){
        performUpdateValidation(email, updateMemberDto);
        Member member = assertUserExistsAndRetrieve(email);
        member.setEmail(updateMemberDto.getEmail());
        member.setFirstName(updateMemberDto.getFirstName());
        member.setLastName(updateMemberDto.getLastName());
        member.setPhotoUrl(updateMemberDto.getPhotoUrl());

        CoacheeMemberUpdatedDto cpu = (CoacheeMemberUpdatedDto) new CoacheeMemberUpdatedDto()
                .withEmail(updateMemberDto.getEmail())
                .withFirstName(updateMemberDto.getFirstName())
                .withLastName(updateMemberDto.getLastName())
                .withPhotoUrl(updateMemberDto.getPhotoUrl())
                .withClassYear(updateMemberDto.getClassYear())
                .withYoucoachRole(updateMemberDto.getYoucoachRole());
        if(!email.equals(updateMemberDto.getEmail())) {
            String jwtSecret = environment.getProperty("jwt.secret");
            cpu.setToken(securedUserService.generateAuthorizationBearerTokenForUser(updateMemberDto.getEmail(),jwtSecret));
        }
        return cpu;
    }


    public SecuredUserDto getUserById(long id) {
        return memberMapper.toUserDto(memberRepository.findById(id).get());
    }

    public List<SecuredUserDto> getAllusers() {
        return memberMapper.toUserDto(memberRepository.findAll());
    }

    public boolean emailExists(String email){
        return memberRepository.existsByEmail(email);
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
    public void performUpdateValidation(String email, UpdateMemberDto updateMemberDto) {
        if (!email.equalsIgnoreCase(updateMemberDto.getEmail()) && emailExists(updateMemberDto.getEmail())) {
        throw new IllegalStateException("Email already exists!");
    }
}

    public CoacheeProfileDto getCoacheeProfile(long id){
        Member member = assertUserExistsAndRetrieve(id);
        return memberMapper.toCoacheeProfileDto(member);
    }

    public CoacheeProfileDto getCoacheeProfile(String email){
        Member member = assertUserExistsAndRetrieve(email);
        return memberMapper.toCoacheeProfileDto(member);
    }

    public CoachProfileDto getCoachProfileForUser(Member member){
        Coach coach = assertCoachExistsAndRetrieve(member);
        return memberMapper.toCoachProfileDto(coach);
    }
    public CoachProfileDto getCoachProfileForUserWithEmail(String email){
        Coach coach = assertCoachExistsAndRetrieve(email);
        return memberMapper.toCoachProfileDto(coach);
    }

    public CoachProfileDto updateCoachInformation(String email, CoachProfileDto coachProfileDto){
        Coach coach = assertCoachExistsAndRetrieve(email);
        coach.setAvailability(coachProfileDto.getAvailability());
        coach.setIntroduction(coachProfileDto.getIntroduction());
        return coachProfileDto;
    }

    public Coach assertCoachExistsAndRetrieve(String email){
        return coachRepository.findCoachByMember_Email(email).orElseThrow(CoachNotFoundException::new);
    }

    public CoachProfileDto getCoachProfile(Principal principal, long id){
        Coach coach = assertCoachExistsAndRetrieve(id);
        CoachProfileDto coachDto = memberMapper.toCoachProfileDto(coach);

        obliterateEmailForNonAdminsAndStrangers(principal, coachDto);

        return coachDto;
    }

    private void obliterateEmailForNonAdminsAndStrangers(Principal principal, CoachProfileDto coach) {
        if(!securedUserService.isAdmin(principal.getName()) && !coach.getEmail().equals(principal.getName())) coach.setEmail(null);
    }

    public CoachProfileDto getCoachProfile(String id){
        Coach coach = assertCoachExistsAndRetrieve(id);
        return memberMapper.toCoachProfileDto(coach);
    }

    public Coach assertCoachExistsAndRetrieve(long id){
        return coachRepository.findById(id).orElseThrow(CoachNotFoundException::new);
    }

    public Coach assertCoachExistsAndRetrieve(Member member){
        return coachRepository.findCoachByMember(member).orElseThrow(CoachNotFoundException::new);
    }

    private Member assertUserExistsAndRetrieve(long id) {
        return memberRepository.findById(id).orElseThrow(MemberIdNotFoundException::new);
    }
    private Member assertUserExistsAndRetrieve(final String email){
        return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
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

        CoachListingDto cl = memberMapper.toCoachListingDto(coachList);
        cl.getCoaches().forEach(coach -> coach.setEmail(null));
        return cl;
    }
}
