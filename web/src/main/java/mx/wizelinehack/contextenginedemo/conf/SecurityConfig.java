package mx.wizelinehack.contextenginedemo.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The type Security config.
 *
 * @author Armando Montoya,  created on: 6/2/18
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * The Authentication entry point.
     */
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    /**
     * Configure global.
     *
     * @param authentication the authentication
     * @throws Exception the exception
     */
    @Autowired
    public void configureGlobal(
            final AuthenticationManagerBuilder authentication) throws Exception {
        final String encodedPass = encoder.encode("bs(zemV2kv*F`B5r");
        authentication
                .inMemoryAuthentication()
                .withUser("tdYB42tMSapMNzwx")
                .password(encodedPass)
                .roles("APP");
    }
}
