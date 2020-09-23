package newsportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecConfig extends WebSecurityConfigurerAdapter {



    //@SuppressWarnings("deprecation")
    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .and()
                .authorizeRequests()
                .antMatchers("/","/home", "/news", "/home/**", "/hashtagFollowTest").permitAll()
                .antMatchers("/webjars/bootstrap/**", "/webjars/jquery/**", "/webjars/popper.js/**").permitAll()
                .antMatchers("/statistics").hasRole("ADMIN")
                .antMatchers("/users", "/user/changeRole").hasRole("ADMIN")
                .antMatchers("/createNews").permitAll()
                //.antMatchers("/createNews").hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}
