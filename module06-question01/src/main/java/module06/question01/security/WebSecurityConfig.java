package module06.question01.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import static module06.question01.security.SecurityRoles.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RoleHierarchy roleHierarchy;

    // HTTP security configuration
    // for all users, access is permitted to "/", "/home" and "/login", "/login-error", "/logout"
    // the login form is "/login"
    // the login failure url is "/login-error"
    // the logout urls are "/logout" and "/login" (after logout)
    // for other urls we need to check specific roles:
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .expressionHandler(expressionHandler())
                .mvcMatchers("/", "/home").permitAll()
                .mvcMatchers("/employees").hasRole(EMPLOYEES_PAG_VIEW)
                .mvcMatchers("/departments").hasRole(DEPARTMENTS_PAG_VIEW)
                .mvcMatchers("/customers").hasRole(CUSTOMERS_PAG_VIEW)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    // Hardcoded in-memory users for testing purposes
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser("john")
                .password(encoder.encode("john"))
                .roles(SUPER_ADMIN)
                .and()
                .withUser("emma")
                .password(encoder.encode("emma"))
                .roles(EMPLOYEES_ADMIN)
                .and()
                .withUser("william")
                .password(encoder.encode("william"))
                .roles(DEPARTMENTS_PAG_VIEW, DEPARTMENTS_READ, DEPARTMENTS_CREATE)
                .and()
                .withUser("lucas")
                .password(encoder.encode("lucas"))
                .roles(CUSTOMERS_PAG_VIEW, CUSTOMERS_READ)
                .and()
                .withUser("tom")
                .password(encoder.encode("tom"))
                .roles();
    }

    private DefaultWebSecurityExpressionHandler expressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
}
