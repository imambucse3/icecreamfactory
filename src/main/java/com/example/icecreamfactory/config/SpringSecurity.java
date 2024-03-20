package com.example.icecreamfactory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
		    .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) ->{
                    authorize.requestMatchers(HttpMethod.GET, "/", "/login", "/login-error", "/logout","/javascript/**").permitAll();
                    authorize.requestMatchers("/register/**").permitAll();
                    authorize.requestMatchers("/webjars/**").permitAll();
                    authorize.anyRequest().authenticated(); // Allow authenticated access to /api/**
                    }

                ).formLogin(
                        form -> {
                            form.loginPage("/login");
//                            form.loginProcessingUrl("/login");
                            form.defaultSuccessUrl("/products/list");
                        }


                ).logout(
                        logout -> {
                            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
                            logout.deleteCookies("JSESSIONID");
                            logout.invalidateHttpSession(true);
                        }
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
