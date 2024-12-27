package com.example.militanshop.confg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class AuthenticationConfig {

    private final DataSource dataSource;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationConfig(DataSource dataSource, BCryptPasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("SELECT username, password, IF(active = 1, TRUE, FALSE) FROM usr WHERE username = ?")
                .authoritiesByUsernameQuery(
                        "SELECT u.username, CONCAT('ROLE_', ur.roles) " +
                                "FROM usr u " +
                                "JOIN user_role ur ON u.id = ur.user_id " +
                                "WHERE u.username = ?"
                );
    }
}
