package com.concrete.login.model;

import javax.persistence.*;

/**
 * Description: User phone EO
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Entity
@Table
public class CScUserPhoneEO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long phoneId;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String phoneDDD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private CScUserEO userEO;

    /**
     * @return identifier of the phone
     */
    public long getPhoneId() {
        return this.phoneId;
    }

    /**
     * @param aPhoneId identifier of the phone
     */
    public void setPhoneId(long aPhoneId) {
        this.phoneId = aPhoneId;
    }

    /**
     * @return the phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * @param aPhoneNumber the phone number
     */
    public void setPhoneNumber(String aPhoneNumber) {
        this.phoneNumber = aPhoneNumber;
    }

    /**
     * @return area code number
     */
    public String getPhoneDDD() {
        return this.phoneDDD;
    }

    /**
     * @param aPhoneDDD area code number
     */
    public void setPhoneDDD(String aPhoneDDD) {
        this.phoneDDD = aPhoneDDD;
    }

    /**
     * @return user EO
     */
    public CScUserEO getUser() {
        return userEO;
    }

    /**
     * @param aUserEO aUserEO EO
     */
    public void setUser(CScUserEO aUserEO) {
        this.userEO = aUserEO;
    }
}
