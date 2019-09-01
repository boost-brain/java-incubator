package boost.brain.course.tasks;

import boost.brain.course.tasks.controller.dto.TaskDto;
import org.junit.Before;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskControllerTest {

    private static final String LOCAL_HOST = "http://localhost:8081";

    public static final String URL_CREATE = LOCAL_HOST + Constants.TASKS_CONTROLLER_PREFIX + Constants.CREATE_PREFIX;
    public static final String URL_READ = LOCAL_HOST + Constants.TASKS_CONTROLLER_PREFIX + Constants.READ_PREFIX;
    public static final String URL_UPDATE = LOCAL_HOST + Constants.TASKS_CONTROLLER_PREFIX + Constants.UPDATE_PREFIX;
    public static final String URL_DELETE = LOCAL_HOST + Constants.TASKS_CONTROLLER_PREFIX + Constants.DELETE_PREFIX;
    public static final String URL_COUNT = LOCAL_HOST + Constants.TASKS_CONTROLLER_PREFIX + Constants.COUNT_PREFIX;
    public static final String URL_PAGE = LOCAL_HOST + Constants.TASKS_CONTROLLER_PREFIX + Constants.PAGE_PREFIX;
    public static final String URL_FOR = LOCAL_HOST + Constants.TASKS_CONTROLLER_PREFIX + Constants.FOR_PREFIX;
    public static final String URL_FROM = LOCAL_HOST + Constants.TASKS_CONTROLLER_PREFIX + Constants.FROM_PREFIX;
    public static final String URL_IN = LOCAL_HOST + Constants.TASKS_CONTROLLER_PREFIX + Constants.IN_PREFIX;
    public static final String URL_FILTER = LOCAL_HOST + Constants.TASKS_CONTROLLER_PREFIX + Constants.FILTER_PREFIX;

    private TaskDto taskDto = new TaskDto();
    HttpHeaders header = new HttpHeaders();


    @Before
    public void initHeader() {
        header.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    private void initTaskDto() {
        taskDto.setProject(1);
        taskDto.setAuthor("boost.brain@gmail.com");
        taskDto.setImplementer("chernov_serg@mail.ru");
        taskDto.setName("java-incubator");
        taskDto.setText("Утилита тестирования сервиса управления заданиями");
    }

    private TaskDto createTask() {
        initTaskDto();
        HttpEntity<TaskDto> requestBody = new HttpEntity<>(taskDto, header);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TaskDto> result = restTemplate.postForEntity(URL_CREATE, requestBody, TaskDto.class);
        return result.getBody();
    }

    private Map<String, String> getDataToMap(TaskDto taskDto) {
        Map<String, String> result = new HashMap<>();
        if (taskDto == null) return null;

        result.put("project", String.valueOf(taskDto.getProject()));
        result.put("author", taskDto.getAuthor());
        result.put("implementer", taskDto.getImplementer());
        result.put("name", taskDto.getName());
        result.put("text", taskDto.getText());
        return result;
    }

    @Test
    public void createTestWithGoodObject() {
        initTaskDto();
        HttpEntity<TaskDto> requestBody = new HttpEntity<>(taskDto, header);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<TaskDto> result = restTemplate.postForEntity(URL_CREATE, requestBody, TaskDto.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(getDataToMap(taskDto), getDataToMap(result.getBody()));
    }

    @Test
    public void createTestWithBadObject() {
        initTaskDto();
        taskDto.setProject(0); //bad value: id must be greater than 0
        HttpEntity<TaskDto> requestBody = new HttpEntity<>(taskDto, header);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<TaskDto> result = restTemplate.postForEntity(URL_CREATE, requestBody, TaskDto.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            assertEquals(HttpStatus.NOT_FOUND.value(),
                    ((HttpClientErrorException) e).getStatusCode().value());
        }
    }

    @Test
    public void count() {
        createTask();
        RestTemplate restTemplate = new RestTemplate();
        Long count = restTemplate.getForObject(URL_COUNT, Long.class);
        assertNotNull(count);
        assertTrue(count > 0);
    }

    @Test
    public void createStressTest() throws InterruptedException {
        initTaskDto();
        HttpEntity<TaskDto> requestBody = new HttpEntity<>(taskDto, header);
        RestTemplate restTemplate = new RestTemplate();

        System.out.println(new Date(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        long stop = System.currentTimeMillis() + 1000;
        int cntRecord = 0;
        while (System.currentTimeMillis() < stop) {
            ResponseEntity<TaskDto> result = restTemplate.postForEntity(URL_CREATE, requestBody, TaskDto.class);
            cntRecord++;
        }
        System.out.println("Время начала теста: " + new Date(start));
        System.out.println("Время окончания теста: " + new Date(stop));
        System.out.println("Записано " + cntRecord + " записей в БД.");

    }

    @Test
    public void readTest() {
        TaskDto createdTask = createTask();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TaskDto> resRead = restTemplate.getForEntity(URL_READ + "/" + createdTask.getId(), TaskDto.class);
        TaskDto readedTask = resRead.getBody();
        assertEquals(createdTask, readedTask);
    }

    @Test
    public void updateTest() {
        TaskDto createdTask = createTask();
        createdTask.setName("NEW PROJECT");
        RestTemplate restTemplate = new RestTemplate();
//        TaskDto updated = restTemplate.patchForObject(URL_UPDATE, createdTask, TaskDto.class); //Invalid HTTP method: PATCH; nested exception is java.net.ProtocolException
//        assertEquals(createdTask, updated);

    }

    @Test
    public void deleteTest() {
        TaskDto created = createTask();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TaskDto> find = restTemplate.getForEntity(URL_READ + "/" + created.getId(), TaskDto.class);
        restTemplate.delete(URL_DELETE + "/" + created.getId());
//        find = restTemplate. getForEntity(URL_READ + "/" + created.getId(), TaskDto.class); //org.springframework.web.client.HttpClientErrorException$NotFound: 404 null
        assertTrue(find.getStatusCode() != HttpStatus.OK);
    }
}
