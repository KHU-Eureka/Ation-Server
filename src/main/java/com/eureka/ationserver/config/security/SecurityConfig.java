package com.eureka.ationserver.config.security;

import com.eureka.ationserver.config.security.jwt.JwtAuthEntryPoint;
import com.eureka.ationserver.config.security.jwt.JwtAuthTokenFilter;
import com.eureka.ationserver.security.CustomOAuth2UserService;
import com.eureka.ationserver.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImpl userDetailsService;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final JwtAuthEntryPoint unauthorizedHandler;
  private final OAuth2AuthenticationSuccessHandler successHandler;
  private final OAuth2AuthenticationFailureHandler failureHandler;

  @Bean
  public JwtAuthTokenFilter authenticationJwtTokenFilter() {
    return new JwtAuthTokenFilter();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint((AuthenticationEntryPoint) unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        .antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/api/auth/**").permitAll()
        .antMatchers("/api/persona/**").authenticated()
        .and()
        .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService)
        .and()
        .successHandler(successHandler)
        .failureHandler(failureHandler);

    http.addFilterBefore(authenticationJwtTokenFilter(),
        UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {

    web.ignoring().antMatchers(
        "/v2/api-docs", "/configuration/ui",
        "/swagger-resources", "/configuration/security", "/swagger-ui.html",
        "/webjars/**", "/swagger/**");

  }
}
