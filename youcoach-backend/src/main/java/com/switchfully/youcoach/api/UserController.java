package com.switchfully.youcoach.api;

import com.switchfully.youcoach.domain.dtos.CoacheeProfileDto;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.UserDto;
import com.switchfully.youcoach.domain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private final UserService userService;

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody CreateUserDto createUserDto) {
        LOGGER.info("user was added");
        return userService.createUser(createUserDto);
    }

    @PreAuthorize("hasRole('ROLE_COACHEE')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/profile")
    public CoacheeProfileDto getCoacheeProfile(Principal principal){
        return userService.getCoacheeProfile(principal.getName());
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/profile/{id}")
    public CoacheeProfileDto getSpecificCoacheeProfile(@PathVariable("id") long id){
        return userService.getCoacheeProfile(id);
    }
    //endpont updateProfile
//    @PreAuthorize("hasRole('ROLE_COACHEE')")
//    @PutMapping(produces = "application/json;charset=UTF-8", path = "/profile")
//    public CoacheeProfileDto updateCoacheeProfile(Principal principal){
//        return userService.getCoacheeProfile(principal.getName());
//    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidFieldsException(IllegalStateException ex, HttpServletResponse response) throws IOException {
        LOGGER.info(ex.getMessage());
        response.sendError(400, ex.getMessage());
    }
}
