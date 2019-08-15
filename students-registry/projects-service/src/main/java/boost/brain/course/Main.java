package boost.brain.course;

import boost.brain.course.service.ProjectService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main
{
    public static void main( String[] args ) throws IOException {
        SpringApplication.run(Main.class, args);
        System.out.println( "Hello World!" );
        String JSON="{\n" +
                "   \"projectName\": \"name\",\n" +
                "   \"description\": \"blablabla\",\n" +
                "   \"projectUrl\": \"github.com/repo\"\n" +
                "}";
        ProjectService service = new ProjectService();
        service.createProject(JSON);

    }
}
