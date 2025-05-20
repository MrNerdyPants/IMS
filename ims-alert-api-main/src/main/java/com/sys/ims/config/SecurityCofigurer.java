package com.sys.ims.config;

import com.sys.ims.filters.JwtAuthenticationEntryPoint;
import com.sys.ims.filters.JwtRequestFilter;
import com.sys.ims.service.impl.MyUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebMvc
public class SecurityCofigurer {
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    @Lazy
    MyUserDetailsService myUserDetailService;
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(myUserDetailService);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf().disable().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().authorizeRequests()
                .antMatchers("/api/auth/authenticate").permitAll()
                .antMatchers("/api/nauth/login").permitAll()
                .antMatchers("/api/nauth/signup").permitAll()

                .antMatchers("/api/auth/sign-up").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/actuator").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/super-admin/**").hasRole("SUPER_ADMIN")
                .antMatchers("/api/client-admin/**").hasRole("CLIENT_ADMIN")
                .antMatchers("/api/client-employee/**").hasAnyRole("CLIENT_EMPLOYEE", "CLIENT_ADMIN")
                .antMatchers("/api/client-customer/**").hasRole("CLIENT_CUSTOMER")
                .anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authenticationManager(authenticationManager);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).headers().frameOptions().sameOrigin().cacheControl();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Add any custom configuration if needed
        return modelMapper;
    }

}
