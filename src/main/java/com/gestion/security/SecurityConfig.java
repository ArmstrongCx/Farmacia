package com.gestion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Define el bean PasswordEncoder
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/empleados", "/api/empleados/register","/api/empleados/login","/api/accesos/cambiar-contrasena").permitAll()
                        .requestMatchers("/api/productos/**").permitAll()
                        .requestMatchers("/api/proveedores/**").permitAll()
                        .requestMatchers("/api/ventas/**").permitAll()
                        .requestMatchers("/api/ordenes/**").permitAll()
                        .requestMatchers("/api/detalleOrden/registrar").permitAll()
                        .requestMatchers("/api/clientes/**").permitAll()
                        .requestMatchers("/api/dashboard/**").permitAll()

                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }
}