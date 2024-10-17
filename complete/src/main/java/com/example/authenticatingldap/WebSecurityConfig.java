package com.example.authenticatingldap;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;



@Configuration
public class WebSecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/auth/login").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

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
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        return auth.build();
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
