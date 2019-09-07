package boost.brain.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class TasksService {
    public static void main(String[] args){
        SpringApplication.run(TasksService.class, args);
    }
}
