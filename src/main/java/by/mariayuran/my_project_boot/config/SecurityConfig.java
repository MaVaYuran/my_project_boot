package by.mariayuran.my_project_boot.config;

import by.mariayuran.my_project_boot.service.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
//    private UserService userService;
//    private final UserRepository userRepository;
//
//    public SecurityConfig(UserService userService,
//                          UserRepository userRepository) {
//        this.userService = userService;
//        this.userRepository = userRepository;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users/new").hasAuthority("ADMIN")
                        .requestMatchers("/users").hasAnyAuthority("ADMIN", "MANAGER")
                        .anyRequest().permitAll())
                .formLogin(form -> form.loginPage("/login")
                        .failureUrl("/login-error")
                        .loginProcessingUrl("/auth")
                        .permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true))
                .build();


//        return http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth.requestMatchers("app/welcome", "app/add").permitAll()
//                        .requestMatchers("/app/**").authenticated())
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
//                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
