package boost.brain.course;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

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

curl -H "Content-Type:application/json; charset=utf-8" -X POST "http://localhost:8080/createProject" -d "{\"projectName\":\"projname\",\"description\":\"blablabla\",\"projectUrl\":\"github.com\"}"
curl -H "Content-Type:application/json; charset=utf-8" -X GET "http://localhost:8080/findById" -d "{\"projectId\":6}"
curl -H "Content-Type:application/json; charset=utf-8" -X GET "http://localhost:8080/countProjects"
curl -H "Content-Type:application/json; charset=utf-8" -X DELETE "http://localhost:8080/deleteById" -d {\"projectId\":6}
curl -H "Content-Type:application/json; charset=utf-8" -X GET "http://localhost:8080/watch"
curl -H "Content-Type:application/json; charset=utf-8" -X POST "http://localhost:8080/update" -d "{\"projectName\":\"new name\",\"description\":\"Updated!\",\"projectUrl\":\"github.com\",\"projectId\":10}"

 */