package com.psychology.product.config;

import com.psychology.product.constant.ApiKey;
import com.psychology.product.service.impl.UserDetailsServiceImpl;
import com.psychology.product.service.jwt.AuthEntryPointJwt;
import com.psychology.product.service.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;

    private final String REMEMBER_ME_KEY = "uniqueAndSecretKey";

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    private final String entireDirectory = "/**";

    private final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs" + entireDirectory,
            "/swagger-ui" + entireDirectory
    };

    private final String[] CLOSE_CONTROLLER_WHITELIST = {
            ApiKey.DIAGNOSTIC + entireDirectory,
            ApiKey.CARD + entireDirectory,
            ApiKey.USERS + entireDirectory,
            ApiKey.ADMIN + entireDirectory
    };

    private final String[] OPEN_CONTROLLER_WHITELIST = {
            ApiKey.AUTH + entireDirectory
    };

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService);
        rememberMeServices.setTokenValiditySeconds(86400);
        return rememberMeServices;
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider(REMEMBER_ME_KEY);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(OPEN_CONTROLLER_WHITELIST).permitAll()
                        .requestMatchers(CLOSE_CONTROLLER_WHITELIST).authenticated()
                        .anyRequest().denyAll()
                )
                .httpBasic(withDefaults())
                .rememberMe((rememberMe) -> rememberMe
                        .key(REMEMBER_ME_KEY)
                        .rememberMeServices(rememberMeServices())
                );
        return http.build();
    }
}