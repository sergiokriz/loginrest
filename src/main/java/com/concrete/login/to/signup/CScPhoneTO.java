package com.concrete.login.to.signup;

/**
 * Description: Phone transfer object
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/


public class CScPhoneTO {

    private String ddd;
    private String number;

    /**
     * @return code area
     */
    public String getDdd() {
        return ddd;
    }

    /**
     * @param aDdd code area
     */
    public void setDdd(String aDdd) {
        this.ddd = aDdd;
    }

    /**
     * @return phone number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param aPhoneNumber phone aPhoneNumber
     */
    public void setNumber(String aPhoneNumber) {
        this.number = aPhoneNumber;
    }
}
