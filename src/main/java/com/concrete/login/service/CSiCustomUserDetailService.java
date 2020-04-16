package com.concrete.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: Custom user detail service
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Component
public class CSiCustomUserDetailService implements UserDetailsService {

    @Autowired
    CScUserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String aUserName) throws UsernameNotFoundException {

        User springUser = userFacade.findByEmail(aUserName)
                .map(userEO -> {

                    String authNameList = userEO.getUserRoleSet().stream()
                            .map(e -> e.getUserRole().name())
                            .collect(Collectors.joining(","));

                    List<GrantedAuthority> authList = AuthorityUtils.createAuthorityList(authNameList);

                    User user = new User(userEO.getEmail(),
                            userEO.getPassword(),
                            authList);

                    return user;
                }).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return springUser;
    }
}
