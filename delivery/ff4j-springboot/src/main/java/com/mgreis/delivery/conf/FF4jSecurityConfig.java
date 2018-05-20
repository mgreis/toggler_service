package com.mgreis.delivery.conf;

import org.ff4j.web.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Implementing authentication on top of API
 *
 * @author Cedrick LUNVEN (@clunven)
 */
@Configuration
@EnableWebSecurity
public class FF4jSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private ApiConfig apiConfig;

    /**
     * API REST fined grained tests.
     */
    public FF4jSecurityConfig(ApiConfig apiConfig) {
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("rawtypes")
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
        // Load APIConfig Users intoSpring security in Memory ...
        UserDetailsManagerConfigurer config =  auth.inMemoryAuthentication();
        if (apiConfig.isAuthenticate()) {
            int count = apiConfig.getUsers().keySet().size();
            int idx = 0;
            for (String currentUser :  apiConfig.getUsers().keySet()) {
                UserDetailsBuilder udb = config.withUser(currentUser)
                    .password(apiConfig.getUsers().get(currentUser))
                    .roles(apiConfig.getPermissions().get(currentUser).toArray(new String[0]));
                // There is another user to use
                if (idx++ <count) {
                    config = udb.and();
                }
            }
        }
    }
 
    /** {@inheritDoc} */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (apiConfig.isAuthenticate()) {
            // ENFORCE AUTHENTICATION
            http.httpBasic().
            // DISABLE CSRF
            and().csrf().disable().
            authorizeRequests()
                .antMatchers("/ff4j-web-console/**").hasRole("ADMIN")
                .antMatchers("/api/**").hasRole("USER")
                .antMatchers("/").permitAll()
            .anyRequest().authenticated();
        }
        else {
            http.httpBasic().and().csrf().disable();
        }
    }





}
