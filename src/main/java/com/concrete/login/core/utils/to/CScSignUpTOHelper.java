package com.concrete.login.core.utils.to;

import com.concrete.login.model.CScUserEO;
import com.concrete.login.to.signup.CScSignUpResponseTO;

/**
 * Description: Signup TO helper
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScSignUpTOHelper {

    /**
     * Fills the TO
     * @param aSignUpResponseTO TO
     * @param aUserEO user EO
     * @return Filled TO
     */
    public static CScSignUpResponseTO fillSignUpResponseTO(CScSignUpResponseTO aSignUpResponseTO,
                                                           CScUserEO aUserEO) {

        aSignUpResponseTO.setId(aUserEO.getUserId());
        aSignUpResponseTO.setCreated(aUserEO.getCreatedAt());
        aSignUpResponseTO.setModified(aUserEO.getModifiedAt());
        aSignUpResponseTO.setLastLogin(aUserEO.getLastLoginAt());
        aSignUpResponseTO.setToken(aUserEO.getJwt());

        return aSignUpResponseTO;
    }

}
