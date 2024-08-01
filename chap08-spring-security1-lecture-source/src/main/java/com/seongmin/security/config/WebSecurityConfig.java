package com.seongmin.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /*
     * 정적파일에 대해서 인증, 인가 검사를 수행하지 않게 설정
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("css/**", "js/**", "images/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((registry) -> {
            registry.requestMatchers("/", "index.html").permitAll() // permitAll() : 누구나 허용
                    .requestMatchers("board/**").authenticated() // authenticated() : 인증된 사용자만 허용
                    .requestMatchers("admin/**").hasRole("ADMIN") // hasRole(), hasAnyRole() : 특정 권한이 있는 사용자만 허용
                    .anyRequest().authenticated(); // 번외 anonymous() : 인증되지 않은 사용자만 허용
//            registry.antRequest().permitAll(); // 모든 요청허용
        });

        // 로그인 설정
        http.formLogin(configurer -> {
            configurer.loginPage("/auth/login") // Get로그인폼 요청
                    .loginProcessingUrl("/auth/login") // POST 로그인처리 URL
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll();
        });

        // 로그아웃 설정
        http.logout(configurer -> {
            configurer.logoutUrl("/auth/logout") // 로그아웃 지정 url
                    .logoutSuccessUrl("/auth/login"); // 로그아웃 후 리다이렉트 할 url
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Spring Security에서 관리되는 사용자 타입
     * (임시로 메모리상에 사용자 등록해서 테스트)
     * - 사용자 = UserDetails 타입
     * - 사용자 조회 서비스 = UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {

        // 일반사용자
        UserDetails user = User.builder()
                .username("user01")
                .password(passwordEncoder().encode("1234")) // 1234를 암호화해서 저장
                .roles("USER")
                .build();

        // 관리자
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("1234"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
