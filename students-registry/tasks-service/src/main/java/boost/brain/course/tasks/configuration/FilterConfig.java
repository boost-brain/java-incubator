package boost.brain.course.tasks.configuration;

import boost.brain.course.common.auth.bean.CheckHeaderSession;
import boost.brain.course.common.auth.bean.CheckHeaderSessionImpl;
import boost.brain.course.common.auth.filter.CheckSessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("default")
public class FilterConfig {

    @Value("${auth-login-check-url}")
    private String authUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public FilterConfig(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Bean
    public CheckHeaderSession getCheckHeaderSession(){
        return new CheckHeaderSessionImpl(authUrl, restTemplate);
    }

    @Bean
    public CheckSessionFilter getCheckSessionFilter() {
        return new CheckSessionFilter(getCheckHeaderSession());
    }

    @Bean
    public FilterRegistrationBean<CheckSessionFilter> checkSessionFilter(){
        FilterRegistrationBean<CheckSessionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(getCheckSessionFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}