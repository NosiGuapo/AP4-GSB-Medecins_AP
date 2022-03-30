package io.github.nosiguapo.ap4medecins.security;

import io.github.nosiguapo.ap4medecins.filters.AuthFilter;
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

import static org.springframework.http.HttpMethod.*;

// We extend configurerAdapter in order to rewrite some propreties of it
@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Beans to pre-existing spring security classes
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
        AuthFilter authFilter = new AuthFilter(authenticationManagerBean());
        authFilter.setFilterProcessesUrl("/gsb/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Allowing anyone to log in
        // (Allows /gsb/login/** AND /gsb/login itself)
        http.authorizeRequests().antMatchers("/gsb/login/**").permitAll();

        // / Permits
        // | Members can execute all GET requests
        // | Only admins may perform POST, PUT and DELETE requests
        // |
        // | We split all categories to apply further modifications with much more ease
        // | (E.g: Restricting doctor data access to Non-admin, ...)
        http.authorizeRequests().antMatchers(GET, "gsb/medecins/**").hasAnyAuthority("ROLE_MEMBRE");
        http.authorizeRequests().antMatchers(GET, "gsb/departements/**").hasAnyAuthority("ROLE_MEMBRE");
        http.authorizeRequests().antMatchers(GET, "gsb/pays/**").hasAnyAuthority("ROLE_MEMBRE");

        http.authorizeRequests().antMatchers(GET, "gsb/utilisateurs").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "gsb/roles").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "gsb/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "gsb/**").hasAnyAuthority("ROLE_ADMIN");
        // A POST request is sent in order to log in, we split POSTS
        http.authorizeRequests().antMatchers(POST, "gsb/medecins/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "gsb/pays/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "gsb/departements/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "gsb/roles").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "gsb/utilisateurs").hasAnyAuthority("ROLE_ADMIN");

        // Restric all other operations
        // Important to place this (and filter) after the previous antMatchers
        http.authorizeRequests().anyRequest().authenticated();

        // / Filters
        // | Since we have modified the authFilter earlier on (to modify login page URL, we pass the object)
//        http.addFilter(new AuthFilter(authenticationManagerBean()));
        http.addFilter(authFilter);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
