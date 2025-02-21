package customer.feedback.com.configuration;

import customer.feedback.com.exception.CustomerFeedbackAuthenticationEntryPoint;
import customer.feedback.com.model.Role;
import customer.feedback.com.security.JwtAuthorizationFilter;
import customer.feedback.com.service.UserService;
import customer.feedback.com.utility.RestAPIConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomerFeedbackAuthenticationEntryPoint customerFeedbackAuthenticationEntryPoint;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    private static final String[] UNAUTHENTICATED_API_LIST = {
            RestAPIConstants.AUTHENTICATION_LOGIN
    };

    private static final String[] AUTHENTICATED_USER_AND_ADMIN_API_LIST = {
           "/"
    };

    private static final String[] AUTHENTICATED_ADMIN_API_LIST = {
            RestAPIConstants.REGISTER
    };

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // Enable CORS and disable CSRF
        httpSecurity.cors().and().csrf().disable();

        // Set permissions on endpoints
        httpSecurity.authorizeHttpRequests(authorize ->{
                    authorize.requestMatchers(UNAUTHENTICATED_API_LIST).permitAll();
                    authorize.requestMatchers(AUTHENTICATED_USER_AND_ADMIN_API_LIST).hasAnyAuthority(Role.ADMIN.toString(), Role.USER.toString());
                    authorize.requestMatchers(AUTHENTICATED_ADMIN_API_LIST).hasAuthority(Role.ADMIN.toString());
                    authorize.anyRequest().authenticated();
                });

        // Set session management to stateless
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        // Set unauthorized requests exception handler
        httpSecurity.exceptionHandling().authenticationEntryPoint(customerFeedbackAuthenticationEntryPoint);

        httpSecurity.authenticationProvider(authenticationProvider());

        // Add security filter
        httpSecurity.authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);



        return httpSecurity.build();
    }

}
