package ru.ancndz.libraryBase.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.ancndz.libraryBase.configs.services.LoginService;
import ru.ancndz.libraryBase.configs.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginService loginService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    //Доступ только для не зарегистрированных пользователей
                    //.antMatchers("/registration/**").not().fullyAuthenticated()
                    //.antMatchers("/registration/save/**").not().fullyAuthenticated()
                    //Доступ только для пользователей с ролью Администратор
                    .antMatchers("/registration/staff/**").hasRole("ADMIN")
                    .antMatchers("/registration/staff/save/**").hasRole("ADMIN")
                    .antMatchers("/staff/edit/**").hasRole("ADMIN")
                    .antMatchers("/staff/delete/**").hasRole("ADMIN")
                    .antMatchers("/staff/save/**").hasRole("ADMIN")
                    .antMatchers("/jobs/edit/**").hasRole("ADMIN")
                    .antMatchers("/jobs/delete/**").hasRole("ADMIN")
                    .antMatchers("/jobs/save/**").hasRole("ADMIN")
                    .antMatchers("/jobs/**").hasRole("ADMIN")
                    .antMatchers("/libs/edit/**").hasRole("ADMIN")
                    .antMatchers("/libs/delete/**").hasRole("ADMIN")
                    .antMatchers("/libs/new/**").hasRole("ADMIN")
                    .antMatchers("/libs/save/**").hasRole("ADMIN")
                    //Для пользователей
                    .antMatchers("/books/").hasRole("USER")
                    //Доступ разрешен всем пользователей
                    .antMatchers("/books/", "/libs/", "/books/filter","/test/index","/fragments/header").permitAll()
                    .antMatchers("/registration/**").permitAll()
                    .antMatchers("/registration/save/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                    //Настройка для входа в систему
                    .formLogin()
                    .loginPage("/login")
                    //Перенарпавление на главную страницу после успешного входа
                    .defaultSuccessUrl("/")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/").and()
                .csrf()
                .disable();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(bCryptPasswordEncoder());
    }
}
