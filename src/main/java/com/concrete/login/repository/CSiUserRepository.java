package com.concrete.login.repository;

import com.concrete.login.model.CScUserEO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Description: User repository
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public interface CSiUserRepository extends CrudRepository<CScUserEO, Long> {

    /**
     * Finds an user by id
     *
     * @param aId id of the user
     * @return EO
     */
    Optional<CScUserEO> findByid(Long aId);

    /**
     * Finds an user by the name
     *
     * @param aUserName user name
     * @return EO
     */
    Optional<CScUserEO> findByuserName(String aUserName);

    /**
     * Finds an user by email
     *
     * @param aUserEmail user email
     * @return EO
     */
    Optional<CScUserEO> findByemail(String aUserEmail);
}
