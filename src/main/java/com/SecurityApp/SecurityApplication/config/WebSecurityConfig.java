package com.SecurityApp.SecurityApplication.config;

import com.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.SecurityApp.SecurityApplication.filters.JwtAuthFilter;
import com.SecurityApp.SecurityApplication.handlers.OAuth2SuccessHandler;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.SecurityApp.SecurityApplication.entities.enums.Permission.*;
import static com.SecurityApp.SecurityApplication.entities.enums.Role.*;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes={"/error","/auth/**","/home.html"};
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){

        httpSecurity.authorizeHttpRequests(auth->auth.requestMatchers(publicRoutes).permitAll()
//                    .requestMatchers("/post/**").hasAnyRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/post/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/post/**").hasAnyRole(ADMIN.name(),CREATOR.name())
                    .requestMatchers(HttpMethod.POST,"/post/**").hasAuthority(POST_CREATE.name())
                    .requestMatchers(HttpMethod.PUT,"/post/**").hasAuthority(POST_UPDATE.name())
                    .requestMatchers(HttpMethod.POST,"/post/**").hasAuthority(POST_VIEW.name())
                    .requestMatchers(HttpMethod.DELETE,"/post/**").hasAuthority(POST_DELETE.name())
                    .anyRequest().authenticated())
//            .csrf(csrfConfig->csrfConfig.disable())
            .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .oauth2Login(oauth2Config->oauth2Config.failureUrl("/login?error=true")
                    .successHandler(oAuth2SuccessHandler));//successful request will come here

//       .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }
//    @Bean
//    UserDetailsService myInMemoryUserDetailsService(){
//        UserDetails normalUser= User.withUsername("istiak").password(passwordEncoder().encode("istiak")).roles("User").build();
//
//        UserDetails adminUser=User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }

}
