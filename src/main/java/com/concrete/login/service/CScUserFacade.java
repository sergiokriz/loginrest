package com.concrete.login.service;

import com.concrete.login.exception.response.CSxConflictException;
import com.concrete.login.exception.response.CSxRequestPageNotFoundException;
import com.concrete.login.model.CScUserEO;
import com.concrete.login.model.CScUserPhoneEO;
import com.concrete.login.model.CScUserRoleEO;
import com.concrete.login.model.CSnUserRole;
import com.concrete.login.security.CSiJwtTokenResolver;
import com.concrete.login.core.utils.to.CScSignUpTOHelper;
import com.concrete.login.repository.CSiUserRepository;
import com.concrete.login.to.signup.CScPhoneTO;
import com.concrete.login.to.signup.CScSignUpRequestTO;
import com.concrete.login.to.signup.CScSignUpResponseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Description: User facade
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Service
public class CScUserFacade {

    @Autowired
    CSiUserRepository userRepository;

    @Autowired
    com.concrete.login.repository.CSiUserPhoneRepository CSiUserPhoneRepository;

    @Autowired
    com.concrete.login.repository.CSiUserRoleRepository CSiUserRoleRepository;

    @Autowired
    CSiJwtTokenResolver jwtTokenResolver;

    /**
     * Returns all the users
     *
     * @return list of all users
     */
    public Iterable<CScUserEO> findAll() {
        return userRepository.findAll();
    }

    /**
     * Find the user by a given email
     *
     * @param aUserEmail user email
     * @return EO
     */
    public Optional<CScUserEO> findByEmail(String aUserEmail) {
        return userRepository.findByemail(aUserEmail);
    }

    /**
     * Registers a new user
     *
     * @param aSignUpRequestTO user transfer object
     * @return response TO
     */
    @Transactional(rollbackFor = Throwable.class)
    public CScSignUpResponseTO registerUser(CScSignUpRequestTO aSignUpRequestTO) {

        CScUserEO newUser = userRepository.findByemail(aSignUpRequestTO.getEmail())
                .orElse(null);

        if (newUser != null) {
            throw new CSxConflictException("Email ja cadastrado!");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(aSignUpRequestTO.getPassword());

        newUser = new CScUserEO();
        newUser.setUserName(aSignUpRequestTO.getName());
        newUser.setEmail(aSignUpRequestTO.getEmail());
        newUser.setPassword(encryptedPassword);
        userRepository.save(newUser);

        String jwt = jwtTokenResolver.generateTokenWithPrefix(aSignUpRequestTO.getEmail());
        newUser.setJwt(jwt);

        for (CScPhoneTO phoneTO : aSignUpRequestTO.getPhones()) {
            CScUserPhoneEO newPhone = new CScUserPhoneEO();
            newPhone.setPhoneDDD(phoneTO.getDdd());
            newPhone.setPhoneNumber(phoneTO.getNumber());
            newPhone.setUser(newUser);
            CSiUserPhoneRepository.save(newPhone);
        }

        CScUserRoleEO newUserRole = new CScUserRoleEO();
        newUserRole.setUserRole(CSnUserRole.ROLE_USER);
        newUserRole.setUser(newUser);
        CSiUserRoleRepository.save(newUserRole);

        Date currentDate = new Date();
        newUser.setCreatedAt(currentDate);
        newUser.setModifiedAt(currentDate);
        newUser.setLastLoginAt(currentDate);

        CScSignUpResponseTO signUpResponseTO = CScSignUpTOHelper.fillSignUpResponseTO(new CScSignUpResponseTO(),
                newUser);

        return signUpResponseTO;
    }

    /**
     * Registers the login of a given user
     *
     * @param aUserEmail user email
     * @return response TO
     */
    @Transactional(rollbackFor = Throwable.class)
    public CScSignUpResponseTO registerLogin(String aUserEmail) {

        CScUserEO user = userRepository.findByemail(aUserEmail)
                .orElseThrow(() -> new CSxRequestPageNotFoundException("User not found."));

        Date currentDate = new Date();
        user.setLastLoginAt(currentDate);

        CScSignUpResponseTO signUpResponseTO = CScSignUpTOHelper.fillSignUpResponseTO(
                new CScSignUpResponseTO(),
                user);

        return signUpResponseTO;
    }

    /**
     * Gets the user profile
     *
     * @param aUserId the user identifier
     * @return TO
     */
    public CScSignUpResponseTO getUserProfile(Long aUserId) {

        CScUserEO optUserEO =
                userRepository.findById(aUserId)
                        .orElseThrow(() -> new CSxRequestPageNotFoundException("User not found."));

        CScSignUpResponseTO signUpResponseTO
                = CScSignUpTOHelper.fillSignUpResponseTO(new CScSignUpResponseTO(),
                optUserEO);

        return signUpResponseTO;
    }
}
