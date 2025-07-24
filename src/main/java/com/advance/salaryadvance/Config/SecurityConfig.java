//package com.advance.salaryadvance.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/otp/**","/h2-console/**").permitAll()  // Allow OTP endpoints without auth
//                        .anyRequest().authenticated()            // Other endpoints require auth
//                )
//                .formLogin(); // or use .formLogin() if preferred
//
//        return http.build();
//    }
//}
