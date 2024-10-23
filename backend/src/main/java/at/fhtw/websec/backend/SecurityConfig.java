package at.fhtw.websec.backend;

import at.fhtw.websec.backend.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    final UserService userService;
    final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors
                        .configurationSource(apiConfigurationSource())
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic((conf) -> conf
                        .authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint())
                )
                .authenticationManager(authenticationManager());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public CorsConfigurationSource apiConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class NoPopupBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException, ServletException {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }
    }
}
