package net.asg.games.yokel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import javax.sql.DataSource;

@Configuration
// Make the below class to extend WebSecurityConfigurerAdapter
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    WebSecurityConfiguration(){

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http.csrf().disable().
                authorizeRequests()
                //.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                //.permitAll()
                //.antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
                //.permitAll()
                //.antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL)
                //.permitAll()
                //.antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
                //.permitAll()
                .antMatchers("/" + h2ConsolePath + "/**")
                .permitAll();
                //.anyRequest().authenticated().and()
                //.addFilter( new AuthenticationFilter(authenticationManager()))
                //.addFilter(getJWTAuthenticationFilter()) // To create a custom URL for authenticaiton filter
                //.addFilter( new AuthorizationFilter(authenticationManager()))
                //.sessionManagement()
                //.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
    }

}