package com.concrete.login.controller;

import com.concrete.login.security.CScSecurityConstants;
import com.concrete.login.service.CScUserFacade;
import com.concrete.login.to.signup.CScSignUpRequestTO;
import com.concrete.login.to.signup.CScSignUpResponseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description: User Controller
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@RestController
@RequestMapping("v1")
public class CScUserController {

    @Autowired
    CScUserFacade userFacade;

    /**
     * Entry point for signing up new users to the Concrete Challenge
     *
     * @param aSignUpRequestTO request body with a new user to be signed up to the system
     * @return response entity object
     */
    @PostMapping(path = CScSecurityConstants.SIGN_UP_URL)
    public ResponseEntity<?> registerUser(@RequestBody CScSignUpRequestTO aSignUpRequestTO) {
        CScSignUpResponseTO signUpResponseTO = userFacade.registerUser(aSignUpRequestTO);
        return new ResponseEntity<>(signUpResponseTO, HttpStatus.OK);
    }

    /**
     * Entry point that returns the list of all users
     *
     * @return response entity object with the list of all users
     */
    @GetMapping(path = "protected/users")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userFacade.findAll(), HttpStatus.OK);
    }

    /**
     * Entry point for a single user information retrieval
     *
     * @param aId user id to be found
     * @return response entity object with the user's information regarding all the authentication process
     */
    @GetMapping(path = "protected/users/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable(value = "id") Long aId) {
        CScSignUpResponseTO signUpResponseTO = userFacade.getUserProfile(aId);
        return new ResponseEntity<>(signUpResponseTO, HttpStatus.OK);
    }
}
