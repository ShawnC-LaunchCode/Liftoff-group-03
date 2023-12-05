package org.launchcode.tutorconnector.config;

import org.launchcode.tutorconnector.controllers.TutorAuthController;
import org.launchcode.tutorconnector.controllers.UserAuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Autowired
    TutorAuthController tutorAuthController;

    @Autowired
    UserAuthController userAuthController;

    //password encoding and decoding. stores and compares passwords

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Permits access to certain URLs without authentication ("register", "home", "logout)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers("/home").permitAll() //public access paths
                .requestMatchers("/tutor/").hasRole("tutor") //paths for tutor
                .requestMatchers("/user/").hasRole("user") //paths for user
                .anyRequest().authenticated() // requires authentication for any other request that does not have a pattern
                .and()
                .formLogin(
                        form -> form
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login") //where the login form should be submitted
                                .defaultSuccessUrl("/users", true) // Redirect to "/users" for users
                                .defaultSuccessUrl("/tutors", true) // Redirect to "/tutors" for tutors
                                .permitAll()
                ).logout(
                        logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()//public access path
        );
        return http.build();

    }
}
