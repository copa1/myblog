package com.copa.config;

import com.copa.service.security.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * SpringSecurity配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许所有用户访问
        http
                .authorizeRequests()
                .antMatchers("/","/index","/aboutme","/archives","/categories","/friendlylink","/tags","/update")
                .permitAll()
                //其他地址的访问均需验证权限
                .antMatchers("/mylove","/editor","/user").hasAnyRole("USER")
                .antMatchers("/ali").hasAnyRole("ADMIN")
                .antMatchers("/superadmin").hasAnyRole("SUPERADMIN")
                .and()
                //开启登录功能，效果，如果没有登陆，没有权限就会来到登录页面
                //登录成功后默认跳转到"/"
                //登录失败重定向到/login?error
                .formLogin().loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/")
                .and()
                //退出登录后的默认url是“/”
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");

        //解决非thymeleaf的form表单提交被拦截问题
        http.csrf().disable();

        //解决中文乱码问题
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
    }

}
