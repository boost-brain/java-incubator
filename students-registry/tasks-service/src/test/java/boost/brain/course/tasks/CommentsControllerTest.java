package boost.brain.course.tasks;

import boost.brain.course.common.tasks.CommentDto;
import boost.brain.course.common.tasks.TaskDto;
import boost.brain.course.tasks.controller.CommentsController;
import boost.brain.course.tasks.repository.CommentsRepository;
import boost.brain.course.tasks.repository.TasksRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static boost.brain.course.tasks.Constants.*;
import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedH2Test
@Log
public class CommentsControllerTest {
    @Autowired
    private CommentsController commentsController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testDoControllerExists() {
        log.info("тест на существование контроллера- CommentsController");
        assertThat(commentsController).isNotNull();
    }

    @Test
    public void testCreateCommentWithFineFields() {
        log.info("Тест на запись хорошего комментария");
        CommentDto commentSample = makeCommentSample();
        try {
            ResultActions resultActions = postCommentToApi(commentSample);
            assertThat(resultActions.andReturn().getResolvedException()).isNull();
            CommentDto commentReceived = resultToComment(resultActions);
            assert commentReceived != null;
            assertThat(commentReceived.getAuthor().equals(commentSample.getAuthor())).isTrue();
            assertThat(commentReceived.getText().equals(commentSample.getText())).isTrue();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testCreateCommentWithNullAuthor() {
        log.info("Тест на запись  комментария без автора");
        CommentDto commentSample = makeCommentSample();
        commentSample.setAuthor(null);
        try {
            ResultActions resultActions = postCommentToApi(commentSample);
            assertThat(resultActions.andReturn().getResolvedException()).isNotNull();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testCreateCommentWithNullText() {
        log.info("Тест на запись  комментария без Текста");
        CommentDto commentSample = makeCommentSample();
        commentSample.setText(null);
        try {
            ResultActions resultActions = postCommentToApi(commentSample);
            assertThat(resultActions.andReturn().getResolvedException()).isNotNull();
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testCreateCommentWithNullTaskId() {
        log.info("Тест на запись  комментария без Таска");
        CommentDto commentSample = makeCommentSample();
        commentSample.setTaskId(0L);
        try {
            ResultActions resultActions = postCommentToApi(commentSample);
            assertThat(resultActions.andReturn().getResolvedException()).isNotNull();
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testCreateCommentWithBigLengthText() {
        log.info("Тест на запись  комментария c огромным текстом");
        CommentDto commentSample = makeCommentSample();
        String builder = IntStream.range(0, 1024).mapToObj(String::valueOf).collect(Collectors.joining());
        builder = builder.substring(0, 254);
        log.info("В настоящее время тест бессмысленен");

        commentSample.setText(builder);
        try {
            ResultActions resultActions = postCommentToApi(commentSample);
            assertThat(resultActions.andReturn().getResolvedException()).isNull();
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testCreateCommentWithNotEmailAuthor() {
        log.info("Тест на запись  атора c невозможной почтой автора");
        CommentDto commentSample = makeCommentSample();
        commentSample.setAuthor("this is not mail");
        try {
            ResultActions resultActions = postCommentToApi(commentSample);
            assertThat(resultActions.andReturn().getResolvedException()).isNotNull();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testCount() {
        log.info("проверка функции count API (количества комментариев)");
        int numberOfCommentsToAdd = 10;
        try {
            for (int i = 0; i < numberOfCommentsToAdd; i++) {
                makeOneComment();
            }
            ResultActions resultActions = mockMvc.perform(get(COMMENTS_CONTROLLER_PREFIX + COUNT_PREFIX));
            Long count = Long.parseLong(resultActions.andReturn().getResponse().getContentAsString());
            Long countFromBase = commentsRepository.count();
            assertNotNull(count);
            assertEquals(count, countFromBase);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testRead() {
        log.info("Проверка функции read Api (чтение комментария по id");
        try {
            CommentDto commentDto = makeOneComment();
            ResultActions resultActions = mockMvc.perform(get(COMMENTS_CONTROLLER_PREFIX + READ_PREFIX +
                    "/" + commentDto.getId()));
            resultActions.andDo(print()).andExpect(status().isOk());
            CommentDto commentDtoResult = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), CommentDto.class);
            resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.id", is((Integer.parseInt(String.valueOf(commentDtoResult.getId()))))));

        } catch (Exception e) {
            fail("Ошибка записи в базу");
        }
    }

    @Test
    public void testDelete() {
        log.info("Проверка функции delete Api (удаление комментария по id)");
        try {
            CommentDto commentDto = makeOneComment();
            ResultActions resultActions = mockMvc.perform(delete(COMMENTS_CONTROLLER_PREFIX + DELETE_PREFIX +
                    "/" + commentDto.getId()));
            resultActions.andDo(print()).andExpect(status().isOk());
            mockMvc.perform(get(TASKS_CONTROLLER_PREFIX + READ_PREFIX + "/" + commentDto.getId()))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail("Ошибка записи в базу");
        }
    }

    @Test
    public void testUpdate() {
        log.info("Проверка функции update Api ");
        try {
            CommentDto commentDto = makeOneComment();
            commentDto.setText("Update Test");
            mockMvc.perform(patch(COMMENTS_CONTROLLER_PREFIX + UPDATE_PREFIX)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(commentDto)))
                    .andDo(print())
                    .andExpect(status().isOk());

            CommentDto commentDtoResult = commentsRepository.read(commentDto.getId());
            assertEquals(commentDtoResult.getText(), commentDto.getText());

        } catch (Exception e) {
            fail("Ошибка записи в базу");
        }
    }

    @Test
    public void pageTest() {
        log.info("Проверка функции постраничный get( пагинация) ");
        assertTrue("Тест бесполезен, так как пагинация для CommentController не определена.", true);
    }


    @Test
    public void commentsFromTest() {
        log.info("Проверка функции получения списка комментариев к задаче ");
        int testProjectId = 3;
        int number = 20;


        try {
            TaskDto taskDto = fillCommentBaseWithAloneTask(testProjectId, number);
            fillCommentBaseWithDifferentTasks(testProjectId, number);

            ResultActions resultActions = mockMvc.perform(get(COMMENTS_CONTROLLER_PREFIX + FOR_PREFIX +
                    "/" + taskDto.getId()));
            resultActions.andDo(print()).andExpect(status().isOk());
            List<Object> commentDtoList = Arrays.asList(objectMapper.readValue(resultActions.andReturn().getResponse().
                    getContentAsString(), CommentDto[].class));
            assertEquals(number, commentDtoList.size());

        } catch (Exception e) {
            fail("Ошибка записи в базу");
        }
    }

    private void fillCommentBaseWithDifferentTasks(int testProjectId, int number) {
        IntStream.range(0, number).forEach(i -> {
            TaskDto taskDto1 = makeTaskSampleWithDefineProjectId(testProjectId);
            saveCommentToBase(makeCommentSampleWithDefineTaskId(taskDto1.getId()));
        });
    }

    /**
     * Заполняем БД Comment комментариями с одним Task
     *
     * @param testProjectId id Project
     * @param number        количество заполняемых элементов
     * @return объект Task
     */
    private TaskDto fillCommentBaseWithAloneTask(int testProjectId, int number) {
        TaskDto taskDto = makeTaskSampleWithDefineProjectId(testProjectId);
        IntStream.range(0, number).forEach(i -> saveCommentToBase(makeCommentSampleWithDefineTaskId(taskDto.getId())));
        return taskDto;
    }

    /**
     * Делает  в базе одну тестовую запись объекта comment
     */

    private CommentDto makeOneComment() {
        CommentDto commentDto = makeCommentSample();
        return saveCommentToBase(commentDto);
    }

    /**
     * Записывает комментарий в базу напрямую
     *
     * @param commentDto объект комментария
     * @return объект комментария
     */
    private CommentDto saveCommentToBase(CommentDto commentDto) {
        return commentsRepository.create(commentDto);
    }


    /**
     * создает объект Comment c определенным id для Task
     *
     * @param taskId -id щбъекта Task
     * @return созданный объект Comment
     */
    private CommentDto makeCommentSampleWithDefineTaskId(long taskId) {
        CommentDto resultComment = new CommentDto();
        resultComment.setAuthor("author@mailserver.domain");
        resultComment.setText("Тестировочный текст для комментария " + Math.random());
        resultComment.setTaskId(taskId);
        return resultComment;
    }

    /**
     * Создает комментарий, который пропускает проверка в API сервисе
     * Для какждого создаваемого Comment создается свой Task
     *
     * @return Коммент
     */
    private CommentDto makeCommentSample() {
        int projectId = 2;
        CommentDto resultComment = new CommentDto();
        resultComment.setAuthor("author@mailserver.domain");
        resultComment.setText("Тестировочный текст для комментария " + Math.random());
        TaskDto taskDto = makeTaskSampleWithDefineProjectId(projectId);
        resultComment.setTaskId(taskDto.getId());
        return resultComment;
    }


    /**
     * Создает объект TaskDto в БД. c определенным id
     * Нужен, так как в  логике создания Comment есть првоерка на наличие таска в базе
     */
    private TaskDto makeTaskSampleWithDefineProjectId(int projectId) {
        TaskDto taskDto = new TaskDto();
        taskDto.setProject(projectId);
        taskDto.setAuthor("taskauthor@mailserver.domain");
        taskDto.setImplementer("implauthor@mailserver.domain");
        taskDto.setName("заголовок таска " + Math.random());
        taskDto.setText("Текст текст " + Math.random());
        taskDto = tasksRepository.create(taskDto);
        return taskDto;
    }


    /**
     * Делает запрос post к API на создание нового объекта Коммент в базе
     *
     * @param commentEntity -записываемый объект коммента
     * @return результат в ResultActions полученный от тестируемого API
     * @throws Exception пробрасываем исключения выше.
     */
    private ResultActions postCommentToApi(CommentDto commentEntity) throws Exception {
        if (commentEntity == null) {
            return null;
        }
        return mockMvc.perform(post(Constants.COMMENTS_CONTROLLER_PREFIX + CREATE_PREFIX)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(commentEntity))
        );
    }

    /**
     * Метод вычленяет объект Комментария из контента ResultActions, который получен от тестируемого API
     *
     * @param resultActions -ответ от API
     * @return объект комментария, если конетнт в ответе пустой, то null
     * @throws IOException пробрасываем исключения дальше.
     */
    private CommentDto resultToComment(ResultActions resultActions) throws IOException {

        String contentResult = resultActions.andReturn().getResponse().getContentAsString();
        if (contentResult.isEmpty()) {
            return null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(contentResult, CommentDto.class);
        }
    }


}