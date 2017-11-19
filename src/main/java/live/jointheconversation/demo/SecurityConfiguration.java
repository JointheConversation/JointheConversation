package live.jointheconversation.demo;

import live.jointheconversation.demo.services.UserOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserOwnerService userOwner;
    private static SecurityConfiguration ourInstance = new SecurityConfiguration();

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new
                BCryptPasswordEncoder();
    }

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http .formLogin()
                .loginPage ("/login") .defaultSuccessUrl("/index") // users homepage
        .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers ("/", "/logout", "/posts", "/register")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl

                        ("/login?logout")
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/posts/create","/posts/create", "/ads/?/edit"
                ) .authenticated();
    }

    @Override
    protected void configure
            (AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userOwner) .passwordEncoder(passwordEncoder());
    }

}
