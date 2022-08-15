package gr.snika.diorasi.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import gr.snika.diorasi.entities.AppUser;
import gr.snika.diorasi.repositories.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//FIXME Use a BCryptPasswordEncoder
		auth.userDetailsService(userDetailsService()).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/**").authenticated()
			.antMatchers("/home").permitAll()
			.and()
			.httpBasic()
			.authenticationEntryPoint(entryPoint())
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Autowired
	private UserRepository userRepository;
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				AppUser user= userRepository.findByUsername(username);
				if (user == null)
			        throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
			    return (UserDetails) user.asUserDetails();
			}
		};
	}
	
	@Bean
	public AuthenticationEntryPoint entryPoint() {
		return new BasicAuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException {
				try {
					response.addHeader("WWW-Authenticate", "Basic Realm - " + getRealmName());
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentType("application/json");
					response.getWriter()
							.println("HTTP Status 401 - " + authException.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void afterPropertiesSet(){
				setRealmName("diorasiRealm");
				super.afterPropertiesSet();
			}
		};
	}
	
}
