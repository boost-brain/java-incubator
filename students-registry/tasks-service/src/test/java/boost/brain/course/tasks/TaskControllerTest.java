package boost.brain.course.tasks;

import boost.brain.course.tasks.controller.TasksController;
import boost.brain.course.common.tasks.TaskDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static boost.brain.course.tasks.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedH2Test
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TasksController tasksController;

    private ResultActions mockMvcResult;

    private TaskDto taskDtoInstance;
    private TaskDto taskDtoResult;

    private void initInstanceTaskDto() {
        taskDtoInstance = new TaskDto();
        taskDtoInstance.setProject(1);
        taskDtoInstance.setAuthor("boost.brain@gmail.com");
        taskDtoInstance.setImplementer("chernov_serg@mail.ru");
        taskDtoInstance.setName("java-incubator");
        taskDtoInstance.setText("Утилита тестирования сервиса управления заданиями");
    }

    private String asJsonString(final Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void saveInstanceTaskToDB() throws Exception {
        if (taskDtoInstance == null) {
            return;
        }

        mockMvcResult = mockMvc.perform(post(TASKS_CONTROLLER_PREFIX + CREATE_PREFIX)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(taskDtoInstance))
        );

        String contentResult = mockMvcResult.andReturn().getResponse().getContentAsString();
        if (contentResult.isEmpty()) {
            taskDtoResult = null;
        } else {
            taskDtoResult = mapper.readValue(contentResult, TaskDto.class);
        }
    }

    @Test
    public void test() {
        assertThat(tasksController).isNotNull();
    }

    @Test
    public void createGoodObject() throws Exception {
        initInstanceTaskDto();

        String author = "testAuthor@mail.ru";
        taskDtoInstance.setAuthor(author);
        saveInstanceTaskToDB();

        assertThat(mockMvcResult.andReturn().getResolvedException()).isNull();
        mockMvcResult
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.project", is(taskDtoInstance.getProject())))
                .andExpect(jsonPath("$.author", is(author)))
                .andExpect(jsonPath("$.implementer", is(taskDtoInstance.getImplementer())))
                .andExpect(jsonPath("$.name", is(taskDtoInstance.getName())))
                .andExpect(jsonPath("$.text", is(taskDtoInstance.getText())))
                .andExpect(jsonPath("$.createDate").isNotEmpty())
                .andExpect(jsonPath("$.updateDate").isNotEmpty());
    }

    @Test
    public void createWithBadProjectId() throws Exception {
        initInstanceTaskDto();
        taskDtoInstance.setProject(0);
        saveInstanceTaskToDB();
        assertThat(mockMvcResult.andReturn().getResolvedException()).isNotNull();
    }

    @Test
    public void createWithBadAuthor() throws Exception {
        initInstanceTaskDto();
        String author = "BadAuthor";
        taskDtoInstance.setAuthor(author);
        saveInstanceTaskToDB();
        assertThat(mockMvcResult.andReturn().getResolvedException()).isNotNull();
    }

    @Test
    public void createWithEmptyAuthor() throws Exception {
        initInstanceTaskDto();
        String author = "";
        taskDtoInstance.setAuthor(author);
        saveInstanceTaskToDB();
        assertThat(mockMvcResult.andReturn().getResolvedException()).isNotNull();
    }

    @Test
    public void createWithBadImplementer() throws Exception {
        initInstanceTaskDto();
        String implementer = "BadImplementer";
        taskDtoInstance.setImplementer(implementer);
        saveInstanceTaskToDB();
        assertThat(mockMvcResult.andReturn().getResolvedException()).isNotNull();
    }

    @Test
    public void createWithEmptyImplementer() throws Exception {
        initInstanceTaskDto();
        String implementer = "";
        taskDtoInstance.setImplementer(implementer);
        saveInstanceTaskToDB();
        assertThat(mockMvcResult.andReturn().getResolvedException()).isNotNull();
    }

    @Test
    public void createWithEmptyName() throws Exception {
        initInstanceTaskDto();
        String name = "";
        taskDtoInstance.setName(name);
        saveInstanceTaskToDB();
        assertThat(mockMvcResult.andReturn().getResolvedException()).isNotNull();
    }

    @Test
    public void createWithEmptyText() throws Exception {
        initInstanceTaskDto();
        String text = "";
        taskDtoInstance.setText(text);
        saveInstanceTaskToDB();
        assertThat(mockMvcResult.andReturn().getResolvedException()).isNotNull();
    }

    @Test
    public void count() throws Exception {
        initInstanceTaskDto();
        saveInstanceTaskToDB();
        saveInstanceTaskToDB();
        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + COUNT_PREFIX));
        Long count = Long.parseLong(mockMvcResult.andReturn().getResponse().getContentAsString());
        assertNotNull(count);
        assertTrue(count > 0);
    }

    @Test
    public void readTest() throws Exception {
        initInstanceTaskDto();
        saveInstanceTaskToDB();
        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + READ_PREFIX + "/" + taskDtoResult.getId()));
        mockMvcResult.andDo(print())
                .andExpect(status().isOk());
        TaskDto taskDtoResultRead = mapper.readValue(mockMvcResult.andReturn().getResponse().getContentAsString(), TaskDto.class);
        mockMvcResult.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is((Integer.parseInt(String.valueOf(taskDtoResultRead.getId()))))));
    }

    @Test
    public void updateTest() throws Exception {
        initInstanceTaskDto();
        saveInstanceTaskToDB();

        taskDtoResult.setName("NEW PROJECT");
        mockMvcResult = mockMvc.perform(patch(TASKS_CONTROLLER_PREFIX + UPDATE_PREFIX)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(taskDtoResult)))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + READ_PREFIX + "/" + taskDtoResult.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.project", is(taskDtoResult.getProject())));

    }

    @Test
    public void deleteTest() throws Exception {
        initInstanceTaskDto();
        saveInstanceTaskToDB();
        mockMvc.perform(delete(TASKS_CONTROLLER_PREFIX + DELETE_PREFIX + "/" + taskDtoResult.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + READ_PREFIX + "/" + taskDtoResult.getId()))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete(TASKS_CONTROLLER_PREFIX + DELETE_PREFIX + "/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void pageTest() throws Exception {
        initInstanceTaskDto();
        for (int i = 0; i < 20; i++) {
            saveInstanceTaskToDB();
        }

        int page = 2;
        int size = 5;
        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + PAGE_PREFIX + "/" + page + "/" + size ))
                .andDo(print()).andExpect(status().isOk());
        String listTaskAsString = mockMvcResult.andReturn().getResponse().getContentAsString();
        TaskDto[] tasks = mapper.readValue(listTaskAsString, TaskDto[].class);
        for (TaskDto task : tasks) {
            System.out.println(task);
        }

        page = 0;
        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + PAGE_PREFIX + "/" + page + "/" + size ))
                .andDo(print()).andExpect(status().isBadRequest());

        page = 1;
        size = 0;
        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + PAGE_PREFIX + "/" + page + "/" + size ))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void taskForTest() throws Exception {
        initInstanceTaskDto();
        String implementer = "cher@mail.ru";
        taskDtoInstance.setImplementer(implementer);
        for (int i = 0; i < 3; i++) {
            saveInstanceTaskToDB();
        }

        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + FOR_PREFIX + "/" + implementer))
                .andDo(print()).andExpect(status().isOk());
        List<TaskDto> tasks = Arrays.asList(mapper.readValue(mockMvcResult.andReturn().getResponse().getContentAsString(), TaskDto[].class));
        assertThat(tasks.size()).isGreaterThanOrEqualTo(3);

        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + FOR_PREFIX + "/" + "qwe"))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void tasksFromTest() throws Exception {
        initInstanceTaskDto();
        String author = "boostbrain@gmail.com";
        taskDtoInstance.setAuthor(author);
        for (int i = 0; i < 3; i++) {
            saveInstanceTaskToDB();
        }

        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + FROM_PREFIX + "/" + author))
                .andDo(print()).andExpect(status().isOk());
        List<TaskDto> tasks = Arrays.asList(mapper.readValue(mockMvcResult.andReturn().getResponse().getContentAsString(), TaskDto[].class));
        assertThat(tasks.size()).isGreaterThanOrEqualTo(3);

        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + FROM_PREFIX + "/" + "qwe"))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void taskInTest() throws Exception {
        initInstanceTaskDto();
        int project = 777;
        taskDtoInstance.setProject(project);
        for (int i = 0; i < 7; i++) {
            saveInstanceTaskToDB();
        }

        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + IN_PREFIX + "/" + project))
                .andDo(print()).andExpect(status().isOk());
        List<TaskDto> tasks = Arrays.asList(mapper.readValue(mockMvcResult.andReturn().getResponse().getContentAsString(), TaskDto[].class));
        assertThat(tasks.size()).isGreaterThanOrEqualTo(7);

        mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + IN_PREFIX + "/987654321"))
                .andDo(print()).andExpect(jsonPath("$").isEmpty());

        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + IN_PREFIX + "/-222"))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void filterTest() throws Exception {
        initInstanceTaskDto();
        int project = 111222;
        String author = "qwe@qwe.com";
        String implementer = "asd@asd.com";
        taskDtoInstance.setProject(project);
        taskDtoInstance.setAuthor(author);
        taskDtoInstance.setImplementer(implementer);
        for (int i = 0; i < 3; i++) {
            saveInstanceTaskToDB();
        }

        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + FILTER_PREFIX)
                                            .param("project", Integer.toString(project))
                                            .param("author", author)
                                            .param("implementer", implementer)
                                        );
        List<TaskDto> tasks = Arrays.asList(mapper.readValue(mockMvcResult.andReturn().getResponse().getContentAsString(), TaskDto[].class));
        assertThat(tasks.size()).isGreaterThanOrEqualTo(3);

        mockMvcResult = mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + FILTER_PREFIX)
                .param("project", "999555111"))
                .andDo(print())
                .andExpect(jsonPath("$").isEmpty());

    }

}
