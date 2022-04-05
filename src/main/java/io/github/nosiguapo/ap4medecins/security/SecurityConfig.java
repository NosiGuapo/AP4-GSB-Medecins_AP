package io.github.nosiguapo.ap4medecins.security;

import io.github.nosiguapo.ap4medecins.filters.AuthFilter;
import io.github.nosiguapo.ap4medecins.filters.VerifyAuthFilter;
import io.github.nosiguapo.ap4medecins.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

// We extend configurerAdapter in order to rewrite some propreties of it
@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Beans to pre-existing spring security classes
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserService appUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // / Note:
        // |   The order of these matters.
        // |   If you want to authorize certain paths, place it at the beggining of the class
        // |   (before the authorizeRequests restrictions)


        // We instanciate this object in order to change the login page URL
        // (other propreties could be edited)
        AuthFilter authFilter = new AuthFilter(authenticationManagerBean(), appUserService);
        authFilter.setFilterProcessesUrl("/gsb/login");

        http
                .csrf().disable()

                // / Permits
                // | Members can execute all GET requests
                // | Only admins may perform POST, PUT and DELETE requests
                // |
                // | We split all categories to apply further modifications with much more ease
                // | (E.g: Restricting doctor data access to Non-admin, ...)
                .authorizeRequests()
                .antMatchers(DELETE,"/gsb/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(PUT,"/gsb/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(POST,"/gsb/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(GET,"/gsb/**").hasAnyAuthority("ROLE_MEMBRE", "ROLE_ADMIN")

                // Allowing anyone to log in
                // (Allows /gsb/login/** AND /gsb/login itself)
                .antMatchers("/gsb/login/**", "/gsb/token/refresh/**").permitAll()

                // Restric all other operations
                // Important to place this (and filter) after the previous antMatchers
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // / Filters
                // | Since we have modified the authFilter earlier on (to modify login page URL, we pass the object)
//        http.addFilter(new AuthFilter(authenticationManagerBean()));
                // Authentication
                .addFilterBefore(new VerifyAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(authFilter);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
