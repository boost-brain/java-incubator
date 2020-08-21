package boost.brain.course.users.configuration;

import boost.brain.course.common.auth.bean.CheckHeaderSession;
import boost.brain.course.common.auth.bean.CheckHeaderSessionBean;
import boost.brain.course.common.auth.filter.CheckSessionFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class FilterConfig {

    @Value("${auth-login-check-url}")
    private String authUrl;

    @Bean
    public CheckHeaderSession getCheckHeaderSession(){
        return new CheckHeaderSessionBean(authUrl);
    }

    @Bean
    public FilterRegistrationBean<CheckSessionFilter> checkSessionFilter(){
        FilterRegistrationBean<CheckSessionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CheckSessionFilter(getCheckHeaderSession()));
        registrationBean.addUrlPatterns("/api/users/secured/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}