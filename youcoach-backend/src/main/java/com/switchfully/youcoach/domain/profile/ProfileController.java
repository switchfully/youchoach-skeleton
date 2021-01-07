package com.switchfully.youcoach.domain.profile;

import com.google.common.collect.ImmutableMap;
import com.switchfully.youcoach.domain.profile.api.ProfileDto;
import com.switchfully.youcoach.domain.profile.api.UpdateProfileDto;
import com.switchfully.youcoach.domain.profile.image.ImageService;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachListingDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachProfileDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachingTopicDto;
import com.switchfully.youcoach.file.DBFile;
import com.switchfully.youcoach.security.authentication.jwt.JwtGenerator;
import com.switchfully.youcoach.security.authorization.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.google.common.collect.ImmutableMap.builder;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin
public class ProfileController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;
    private final AuthorizationService authorizationService;
    private final ImageService imageService;
    private JwtGenerator jwtGenerator;

    @Autowired
    public ProfileController(ProfileService profileService,
                             AuthorizationService authorizationService,
                             ImageService imageService,
                             JwtGenerator jwtGenerator
    ) {
        this.profileService = profileService;
        this.authorizationService = authorizationService;
        this.imageService = imageService;
        this.jwtGenerator = jwtGenerator;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<ProfileDto> getProfiles() {
        return profileService.getProfiles();
    }

    @PreAuthorize("hasAuthority('COACHEE')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/find-coach")
    public CoachListingDto getCoachProfiles() {
        return profileService.getCoachProfiles();
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'COACHEE')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/profile/{id}")
    public ProfileDto getSpecificCoacheeProfile(@PathVariable("id") long id, Authentication principal) {
        if (!authorizationService.canAccessProfile(principal, id)) {
            throw new InsufficientAuthenticationException("You don't have access to this profile");
        }
        return profileService.getCoacheeProfile(id);
    }

    @PreAuthorize("hasAnyAuthority('COACHEE', 'COACH', 'ADMIN')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/coach/profile/{id}")
    public CoachProfileDto getSpecificCoachProfile(Authentication principal, @PathVariable("id") long id) {
        return profileService.getCoachProfile(principal, id);
    }

    @PreAuthorize("hasAnyAuthority('COACHEE', 'ADMIN')")
    @PutMapping(produces = "application/json;charset=UTF-8", consumes = "application/json", path = "/profile/{id}")
    public ResponseEntity<ProfileDto> updateCoacheeProfile(@RequestBody UpdateProfileDto updateProfileDto, @PathVariable("id") long id, Authentication principal) {
        if (!authorizationService.canAccessProfile(principal, id)) {
            throw new InsufficientAuthenticationException("You don't have access to this profile");
        }
        if (!authorizationService.canChangeRole(principal)) {
            updateProfileDto.clearRole();
        }

        ProfileDto profileDto = profileService.updateProfile(id, updateProfileDto);

        return ok()
                .header("Authorization", "Bearer " + profileDto.getToken())
                .header("Access-Control-Expose-Headers", "Authorization")
                .body(profileDto);
    }

    @PreAuthorize("hasAnyAuthority('COACH','ADMIN')")
    @PutMapping(produces = "application/json;charset=UTF-8", path = "/coach/profile/{id}")
    public CoachProfileDto updateCoachInformation(@RequestBody CoachProfileDto coachProfileDto, @PathVariable("id") long id, Authentication principal) {
        if (!authorizationService.canAccessProfile(principal, id)) {
            throw new InsufficientAuthenticationException("You don't have access to this profile");
        }
        String email = profileService.getUserById(id).getEmail();
        return profileService.updateCoachInformation(email, coachProfileDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/coach/profile/{id}/topics")
    public void updateTopics(@RequestBody List<CoachingTopicDto> topicDtos, @PathVariable("id") long id) {
        profileService.updateTopics(id, topicDtos);
    }

    @GetMapping(path = "/topics")
    public List<String> topicList() {
        return profileService.getAllTopics();
    }

    @PreAuthorize("hasAnyAuthority('COACHEE','ADMIN')")
    @PostMapping(path = "/profile/{id}/image")
    public void uploadImage(@RequestParam("profilePicture") MultipartFile file, @PathVariable("id") long id, Authentication principal) {
        if (!authorizationService.canAccessProfile(principal, id)) {
            throw new InsufficientAuthenticationException("You don't have access to this profile");
        }
        imageService.saveProfileImage(id, file);
    }

    @GetMapping(path = "/profile/{id}/image")
    public ResponseEntity<Resource> downloadImage(@PathVariable("id") long id) {
        DBFile profileImage = imageService.getProfileImage(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        return ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profileImage.getOriginalFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                .body(profileImage.getResource());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping(path = "/profile/{id}")
    public void deleteProfile(@PathVariable("id") long id) {
        profileService.deleteProfile(id);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidFieldsException(IllegalStateException ex, HttpServletResponse response) throws IOException {
        LOGGER.info(ex.getMessage());
        response.sendError(400, ex.getMessage());
    }
}
