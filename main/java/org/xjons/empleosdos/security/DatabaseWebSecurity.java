package org.xjons.empleosdos.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity {

    @Bean
    public UserDetailsManager usersCustom(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select username,password,estatus from Usuarios u where username=?");
        users.setAuthoritiesByUsernameQuery(
            "select u.username, p.perfil from UsuarioPerfil up " +
            "inner join Usuarios u on u.id = up.idUsuario " +
            "inner join Perfiles p on p.id = up.idPerfil " +
            "where u.username=?");
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Permitir acceso específico sin autenticación
                .requestMatchers("/signup", "/login", "/home").permitAll()
                .requestMatchers("/vacantes/view/**").permitAll()
                // Restricciones más específicas deben ir primero
                .requestMatchers("/vacantes/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR")
                .requestMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR")
                .requestMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADOR")
                // Recursos estáticos y páginas públicas
                .requestMatchers("/bootstrap/**", "/images/**", "/tinymce/**", "/logos/**").permitAll()
                .requestMatchers("/", "/search", "/bcrypt/**").permitAll()
                // Resto de las peticiones requieren autenticación
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login") // Especificar la página de login personalizada
                .defaultSuccessUrl("/home", true) // Página a la que se redirige tras un login exitoso
                //.failureUrl("/login-error") // Página de error en caso de fallo en el login
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/confirmLogout") // URL para iniciar el proceso de cierre de sesión
                .logoutSuccessUrl("/") // Página a la que se redirige tras un logout exitoso
                .invalidateHttpSession(true) // Invalidar la sesión
                .deleteCookies("JSESSIONID") // Borrar la cookie de sesión
                .permitAll());

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
    }
}
