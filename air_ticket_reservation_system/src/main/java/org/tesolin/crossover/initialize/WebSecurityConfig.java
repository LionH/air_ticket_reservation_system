package org.tesolin.crossover.initialize;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.tesolin.crossover.entity.Role;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(
                		"/",
                		"/*.ico",
                		"/login.html",
                		"/img/*", 
                		"/fonts/*", 
                		"/css/*", 
                		"/js/*", 
                		"/index.html", 
                		"/searchflights/*")
                .permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
    			.and()
    			.httpBasic();;
    }
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.inMemoryAuthentication()
			.withUser("user").password("password").roles(Role.USER.name())
			.and()
			.withUser("admin").password("admin").roles(Role.USER.name(),Role.ADMIN.name());
	}
}
