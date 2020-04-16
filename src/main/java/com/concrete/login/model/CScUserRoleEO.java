package com.concrete.login.model;

import javax.persistence.*;

/**
 * Description: User Role EO
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Entity
@Table
public class CScUserRoleEO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userRoleId;

    @Column
    @Enumerated(EnumType.STRING)
    private CSnUserRole userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private CScUserEO userEO;

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

    /**
     * @return user's role
     */
    public CSnUserRole getUserRole() {
        return userRole;
    }

    /**
     * @param aUserRole user's role
     */
    public void setUserRole(CSnUserRole aUserRole) {
        this.userRole = aUserRole;
    }

}
