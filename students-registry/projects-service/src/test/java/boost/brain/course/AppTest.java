package boost.brain.course;

import static org.junit.Assert.assertTrue;

import boost.brain.course.model.Project;
import boost.brain.course.repository.ProjectRepository;
import boost.brain.course.service.ProjectService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Autowired
    ProjectService projectService;
    @Test
    public void createProjectTest() throws IOException {
        String JSON="{\n" +
                "   \"projectName\": \"name\",\n" +
                "   \"description\": \"blablabla\",\n" +
                "   \"projectUrl\": \"github.com/repo\"\n" +
                "}";
        System.out.println("Testing");
        return;
    }
}

// '{"projcetName"="name","description"="blablabla","projectUrl"="github.com"}

/*

curl -H "Content-Type:application/json; charset=utf-8" -X POST "http://localhost:8080/createProject" -d "{\"projectName\":\"name\",\"description\":\"blablabla\",\"projectUrl\":\"github.com\"}"

 */