package com.concrete.login.config;

import com.concrete.login.security.CSiJwtTokenResolver;
import com.concrete.login.service.CScUserFacade;
import com.concrete.login.service.CSiCustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import static com.concrete.login.security.CScSecurityConstants.SIGN_UP_URL;

/**
 * Description: Security configuration
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CScSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CSiCustomUserDetailService CSiCustomUserDetailService;

    @Autowired
    CSiJwtTokenResolver jwtTokenResolver;

    @Autowired
    private CScUserFacade userFacade;

    @Override
    protected void configure(HttpSecurity aHttp) throws Exception {

       aHttp.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                    .and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/*/" +SIGN_UP_URL).permitAll()
                    .antMatchers("/*/protected/**").hasRole("USER")
                    .antMatchers("/*/admin/**").hasRole("ADMIN")
                    .and()
                    .addFilter(new CScJWTAuthenticationFilter(authenticationManager(), userFacade))
                    .addFilter(new CScJWTAuthorizationFilter(authenticationManager(), jwtTokenResolver, CSiCustomUserDetailService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder aAuthenticationManagerBuilder) throws Exception {
        aAuthenticationManagerBuilder.userDetailsService(CSiCustomUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
