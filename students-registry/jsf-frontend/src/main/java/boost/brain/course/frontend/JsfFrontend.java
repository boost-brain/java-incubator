package boost.brain.course.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@ServletComponentScan
@EnableZuulProxy
@SpringBootApplication
public class JsfFrontend {
    public static void main(String[] args){
        SpringApplication.run(JsfFrontend.class, args);
    }


}
