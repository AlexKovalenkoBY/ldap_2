package com.example.authenticatingldap;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;


@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().fullyAuthenticated()
			)
			.formLogin(Customizer.withDefaults());

		return http.build();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
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

}

//@Configuration
//public class WebSecurityConfig {

//	@Bean
//	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//		return http
//			.authorizeRequests()
//			.anyRequest().authenticated()
//			.and()
//			.formLogin(Customizer.withDefaults())
//			.build();
//	}
//}
