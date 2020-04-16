package com.concrete.login.repository;

import com.concrete.login.model.CScUserEO;
import com.concrete.login.model.CScUserPhoneEO;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Description: User phone repository
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public interface CSiUserPhoneRepository extends CrudRepository<CScUserPhoneEO, Long> {

    /**
     * Finds a user
     * @param aUserEO user entity object
     * @param aSort sorting
     * @return list of phones that belongs to a given user
     */
    List<CScUserPhoneEO> findByUserEO(CScUserEO aUserEO, Sort aSort);
}
