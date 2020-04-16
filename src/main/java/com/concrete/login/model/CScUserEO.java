package com.concrete.login.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Description: User EO
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Entity
public class CScUserEO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String jwt;
    @Column
    private Date createdAt;
    @Column
    private Date modifiedAt;
    @Column
    private Date lastLoginAt;

    @OneToMany(mappedBy = "userEO", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CScUserPhoneEO> phoneSet = new HashSet<>();

    @OneToMany(mappedBy = "userEO", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CScUserRoleEO> userRoleSet = new HashSet<>();

    /**
     * @return the user identifier
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param aUserId user identifier
     */
    public void setUserId(Long aUserId) {
        this.userId = aUserId;
    }

    /**
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param aUserName user name
     */
    public void setUserName(String aUserName) {
        this.userName = aUserName;
    }

    /**
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param aUserPassword user aUserPassword
     */
    public void setPassword(String aUserPassword) {
        this.password = aUserPassword;
    }

    /**
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param aEmail user aEmail
     */
    public void setEmail(String aEmail) {
        this.email = aEmail;
    }

    /**
     * @return user's json web token
     */
    public String getJwt() {
        return jwt;
    }

    /**
     * @param aJwt user's jason web token
     */
    public void setJwt(String aJwt) {
        this.jwt = aJwt;
    }

    /**
     * @return phone set of the user
     */
    public Set<CScUserPhoneEO> getPhoneSet() {
        return phoneSet;
    }

    /**
     * @param aPhoneSet phone set of the user
     */
    public void setPhoneSet(Set<CScUserPhoneEO> aPhoneSet) {
        this.phoneSet = aPhoneSet;
    }

    /**
     * @return user roles
     */
    public Set<CScUserRoleEO> getUserRoleSet() {
        return userRoleSet;
    }

    /**
     * @param aUserRoleSet user roles
     */
    public void setUserRoleSet(Set<CScUserRoleEO> aUserRoleSet) {
        this.userRoleSet = aUserRoleSet;
    }

    /**
     * @return user created at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param aCreatedAt user created at
     */
    public void setCreatedAt(Date aCreatedAt) {
        this.createdAt = aCreatedAt;
    }

    /**
     * @return user modified at
     */
    public Date getModifiedAt() {
        return modifiedAt;
    }

    /**
     * @param aModifiedAt user modified at
     */
    public void setModifiedAt(Date aModifiedAt) {
        this.modifiedAt = aModifiedAt;
    }

    /**
     * @return last login
     */
    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    /**
     * @param aLastLoginAt last login
     */
    public void setLastLoginAt(Date aLastLoginAt) {
        this.lastLoginAt = aLastLoginAt;
    }
}
