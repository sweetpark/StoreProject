package project.movie.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.movie.auth.handler.LogoutSuccessHandler;
import project.movie.auth.jwt.filter.CustomAuthenticationEntryPoint;
import project.movie.auth.jwt.filter.JwtAuthenticationFilter;
import project.movie.auth.jwt.filter.JwtAuthorizationFilter;
import project.movie.auth.jwt.filter.JwtOAuthAuthorizationFilter;
import project.movie.auth.jwt.util.JWTUtil;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final JWTUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtOAuthAuthorizationFilter jwtOAuthAuthorizationFilter;
    // private final NonMemberRepository nonMemberRepository;

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean // Ioc 컨테이너에 BCryptPasswordEncoder() 객체가 등록됨.
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : filterChain 빈 등록됨");
        // iframe 허용안함.
        http
                .headers((auth) -> auth.frameOptions((option) -> option.sameOrigin()));

        // csrf disable
        http
                .csrf((auth) -> auth.disable());

        // From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //HTTP Basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
        // 경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/logout", "/api/members/join", "/guest-login", "/api/items/**", "/naver/login", "/naver/callback").permitAll()
                        .requestMatchers("/page/items/**", "/page/join/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/page/pay/**", "/page/cart/**", "/page/coupons/**").authenticated()
                        .anyRequest().authenticated())
                .exceptionHandling((exceptions) -> exceptions
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));



        http
                // .addFilterBefore(new JwtGuestAuthenticationFilter(nonMemberRepository, jwtUtil), JwtAuthenticationFilter.class) // 비회원 로그인 필터
                .addFilterBefore(jwtOAuthAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) // oauth 필터
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class) // 회원 로그인 필터
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil), JwtAuthenticationFilter.class);

        //세션 설정 : STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // cors 설정
        http
                .cors((corsCustomizer) -> corsCustomizer.configurationSource(configurationSource()));

        // 로그아웃 처리
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSEESSIONID", "Authorization")
                .logoutSuccessHandler(new LogoutSuccessHandler())
                .permitAll());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
