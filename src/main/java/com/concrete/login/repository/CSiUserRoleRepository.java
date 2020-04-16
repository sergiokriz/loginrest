package com.concrete.login.repository;

import com.concrete.login.model.CScUserEO;
import com.concrete.login.model.CScUserRoleEO;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Description: User role repository
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public interface CSiUserRoleRepository extends CrudRepository<CScUserRoleEO, Long> {

    /**
     * Finds the users roles of a given user
     *
     * @param aUserEO user EO
     * @param aSort   sorting
     */
    List<CScUserRoleEO> findByUserEO(CScUserEO aUserEO, Sort aSort);
}
