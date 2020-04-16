package com.concrete.login.to.signup;

import java.util.Set;

/**
 * Description: Sign-up request TO
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScSignUpRequestTO {

    private String name;
    private String email;
    private String password;
    private Set<CScPhoneTO> phones;

    /**
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * @param aUserName user aUserName
     */
    public void setName(String aUserName) {
        this.name = aUserName;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param aEmail aEmail
     */
    public void setEmail(String aEmail) {
        this.email = aEmail;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param aPassword aPassword
     */
    public void setPassword(String aPassword) {
        this.password = aPassword;
    }

    /**
     * @param aPhoneSet phone list
     */
    public void setPhones(Set<CScPhoneTO> aPhoneSet) {
        this.phones = aPhoneSet;
    }

    /**
     * @return phone list
     */
    public Set<CScPhoneTO> getPhones() {
        return phones;
    }

}
