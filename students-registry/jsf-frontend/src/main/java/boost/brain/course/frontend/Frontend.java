package boost.brain.course.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class Frontend {
    public static void main(String[] args){
        SpringApplication.run(Frontend.class, args);
    }


}
