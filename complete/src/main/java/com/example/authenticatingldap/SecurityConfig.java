package com.example.authenticatingldap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

  	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().fullyAuthenticated()
			)
			.formLogin(Customizer.withDefaults());
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth, HttpSecurity http) throws Exception {
		auth
		.ldapAuthentication()
			 .userSearchBase("dc=test,dc=bpab,dc=internal") // База поиска пользователей
			.userSearchFilter("(uid={0})") // Фильтр для поиска пользователя по uid и контейнерам
			  .groupSearchBase("dc=test,dc=bpab,dc=internal") // База поиска групп
			.groupSearchFilter("uniqueMember={0}") // Фильтр для поиска групп
			.contextSource()
			.url("ldap://localhost:8389")
				.and()
			.passwordCompare()
				// .passwordEncoder(new BCryptPasswordEncoder())
				.passwordAttribute("userPassword");
             
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}