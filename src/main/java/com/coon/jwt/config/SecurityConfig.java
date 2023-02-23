package com.coon.jwt.config;

import com.coon.jwt.filter.MyFilter1;
import com.coon.jwt.filter.MyFilter3;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // security보다 먼저 동작하려면 addFilterBefore / SecurityContextPersistenceFilter이 가장 먼저
        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class); // 먼저 실행 후 2 -> 1
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter) //@CrossOrigin(인증X),시큐리티 필터에 등록 인증(ㅇ)
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
    }
}
