package boost.brain.course.dto;

import lombok.Data;

@Data
public class TaskDtoWithNormalDate {
    private long id;
    //id преокта
    private int project;
    //email автора
    private String author;
    //email исполнителя
    private String implementer;
    //Название задания
    private String name;
    //Текст задания
    private String text;
    //Дата создания
    private String createDate;
    //Дата последнего обновления
    private String updateDate;
}
