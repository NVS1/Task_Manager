package ua.kiev.prog.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ua.kiev.prog.model.User;
import ua.kiev.prog.repository.UserRepository;
import ua.kiev.prog.service.UserService;

import java.util.Date;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout").permitAll();
    }
    @Bean
    public PrincipalExtractor principalExtractor (UserRepository userRepository){
        return map -> {
            String id = (String) map.get("sub");
            User user = userRepository.findById(id).orElseGet(() -> {
                User newUser = new User();
                newUser.setId(id);
                newUser.setName((String) map.get("name"));
                newUser.setEmail((String) map.get("email"));
                newUser.setLocale((String) map.get("locale"));
                newUser.setUserPic((String) map.get("picture"));
                return userRepository.save(newUser);
            });
            return user;
        };
    }
}
