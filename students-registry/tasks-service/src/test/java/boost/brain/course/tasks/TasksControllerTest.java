package boost.brain.course.tasks;

import boost.brain.course.common.tasks.TaskDto;
import boost.brain.course.tasks.controller.TasksController;
import boost.brain.course.tasks.controller.exceptions.BadRequestException;
import boost.brain.course.tasks.controller.exceptions.ConflictException;
import boost.brain.course.tasks.controller.exceptions.NotFoundException;
import boost.brain.course.tasks.repository.TasksRepository;
import boost.brain.course.tasks.service.OtherServiceCommunication;
import lombok.extern.java.Log;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@Log
public class TasksControllerTest {
    @Mock
    TasksRepository tasksRepository;
    @Mock
    OtherServiceCommunication otherServiceCommunication;

    @InjectMocks
    TasksController tasksController;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private static TaskDto taskDtoReferenceObject;

    @BeforeClass
    public static void beforeClass() {
        taskDtoReferenceObject = createTaskDtoReferenceObject();
    }

    @Test
    public void testCreate() {
        log.info("тестирование метода create контроллера TasksController");
        when(tasksRepository.create(any())).thenReturn(taskDtoReferenceObject);
        doNothing().when(otherServiceCommunication).updateStatusForUser(any(), any());

        TaskDto result = tasksController.create(taskDtoReferenceObject);

        Assert.assertEquals(taskDtoReferenceObject, result);
        verify(tasksRepository, times(1)).create(any());
    }

    @Test
    public void testRead() {
        log.info("тестирование метода Read контроллера TasksController");
        when(tasksRepository.read(anyLong())).thenReturn(taskDtoReferenceObject);

        TaskDto result = tasksController.read(1L);

        Assert.assertEquals(taskDtoReferenceObject, result);
        verify(tasksRepository, times(1)).read(anyLong());
    }

    @Test
    public void testUpdate() {
        log.info("тестирование метода Update контроллера TasksController");
        when(tasksRepository.read(anyLong())).thenReturn(taskDtoReferenceObject);
        when(tasksRepository.update(any())).thenReturn(true);
        String result = tasksController.update(taskDtoReferenceObject);
        Assert.assertEquals("OK", result);

        // В случае если tasksRepository.update не смог обновить данные, выбрасывается exception ConflictException
        when(tasksRepository.update(any())).thenReturn(false);
        exceptionRule.expect(ConflictException.class);
        tasksController.update(taskDtoReferenceObject);
    }


    @Test
    public void testUpdatePut() {
        log.info("тестирование метода UpdatePut (Patch) контроллера TasksController");
        when(tasksRepository.read(anyLong())).thenReturn(taskDtoReferenceObject);
        when(tasksRepository.update(any())).thenReturn(true);
        String result = tasksController.update(taskDtoReferenceObject);
        Assert.assertEquals("OK", result);

        // В случае если tasksRepository.UpdatePut не смог обновить данные, выбрасывается exception ConflictException
        when(tasksRepository.update(any())).thenReturn(false);
        exceptionRule.expect(ConflictException.class);
        tasksController.update(taskDtoReferenceObject);
    }

    @Test
    public void testDelete() {
        log.info("тестирование метода delete контроллера TasksController");
        when(tasksRepository.delete(anyLong())).thenReturn(true);

        String result = tasksController.delete(1L);
        Assert.assertEquals("OK", result);

        //В случае если tasksRepository.delete не смог удалить, выбрасывается exception NotFoundException
        when(tasksRepository.delete(anyLong())).thenReturn(false);
        exceptionRule.expect(NotFoundException.class);
        tasksController.delete(1L);
    }

    @Test
    public void testCount() {
        long testNumber = 10L;
        log.info("тестирование метода count контроллера TasksController");
        when(tasksRepository.count()).thenReturn(testNumber);

        long result = tasksController.count();
        Assert.assertEquals(testNumber, result);

    }

    @Test
    public void testPage() {
        log.info("тестирование метода getPage контроллера TasksController");
        int page = 20, size = 20;
        when(tasksRepository.getPage(anyInt(), anyInt())).thenReturn(Collections.singletonList(taskDtoReferenceObject));

        List<TaskDto> result = tasksController.page(page, size);
        Assert.assertEquals(Collections.singletonList(taskDtoReferenceObject), result);

        // if (page < 1 || size < 1) {throw new BadRequestException();
        page = 0;
        size = 20;
        exceptionRule.expect(BadRequestException.class);
        tasksController.page(page, size);
        page = 20;
        size = 0;
        tasksController.page(page, size);

        // if (result == null) { throw new ConflictException();
        page = 20;
        size = 20;
        when(tasksRepository.getPage(anyInt(), anyInt())).thenReturn(null);
        exceptionRule.expect(ConflictException.class);
        tasksController.page(page, size);
    }

    @Test
    public void testTasksFor() {
        log.info("тестирование метода tasksFor контроллера TasksController");
        String implementer = "implementer@test.ccom";
        when(tasksRepository.tasksFor(anyString())).thenReturn(Collections.singletonList(taskDtoReferenceObject));

        List<TaskDto> result = tasksController.tasksFor(implementer);
        Assert.assertEquals(Collections.singletonList(taskDtoReferenceObject), result);

        //Проверка e-mail
        implementer = "implementer";
        exceptionRule.expect(BadRequestException.class);
        tasksController.tasksFor(implementer);
    }

    @Test
    public void testTasksFrom() {
        log.info("тестирование метода TasksFrom контроллера TasksController");
        String author = "author@test.com";
        when(tasksRepository.tasksFrom(anyString())).thenReturn(Collections.singletonList(taskDtoReferenceObject));

        List<TaskDto> result = tasksController.tasksFrom(author);
        Assert.assertEquals(Collections.singletonList(taskDtoReferenceObject), result);

        //Проверка e-mail
        author = "author";
        exceptionRule.expect(BadRequestException.class);
        tasksController.tasksFrom(author);
    }

    @Test
    public void testTasksIn() {
        log.info("тестирование метода TasksIn контроллера TasksController");
        int projectNumber = 1;

        when(tasksRepository.tasksIn(anyInt())).thenReturn(Collections.singletonList(taskDtoReferenceObject));

        List<TaskDto> result = tasksController.tasksIn(projectNumber);
        Assert.assertEquals(Collections.singletonList(taskDtoReferenceObject), result);

        // if (project < 1) {        //            throw new BadRequestException();
        projectNumber = 0;
        exceptionRule.expect(BadRequestException.class);
        tasksController.tasksIn(projectNumber);

        //if (result == null) {throw new ConflictException();
        projectNumber = 1;
        when(tasksRepository.tasksIn(anyInt())).thenReturn(null);
        exceptionRule.expect(ConflictException.class);
        tasksController.tasksIn(projectNumber);
    }

    @Test
    public void testFilter() {
        log.info("тестирование метода filter контроллера TasksController");
        int project = 1;
        String author = "author@test.com";
        String implementer = "implementer@test.com";
        when(tasksRepository.filter(anyInt(), anyString(), anyString())).thenReturn(Collections.singletonList(taskDtoReferenceObject));

        List<TaskDto> result = tasksController.filter(project, author, implementer);
        Assert.assertEquals(Collections.singletonList(taskDtoReferenceObject), result);

        // if (result == null) { throw new ConflictException();
        when(tasksRepository.filter(anyInt(), anyString(), anyString())).thenReturn(null);
        exceptionRule.expect(ConflictException.class);
        tasksController.filter(project, author, implementer);

    }

    /*
     * Создание образцового объекта TaskDto
     * @return TaskDto
     */
    private static TaskDto createTaskDtoReferenceObject() {
        TaskDto taskDto = new TaskDto();
        taskDto.setAuthor("Author@email.com");
        taskDto.setCreateDate(1000L);
        taskDto.setId(1);
        taskDto.setImplementer("Implementer@email.com");
        taskDto.setName("Name1");
        taskDto.setProject(3);
        taskDto.setText("text1");
        taskDto.setUpdateDate(2000L);
        return taskDto;
    }


}

