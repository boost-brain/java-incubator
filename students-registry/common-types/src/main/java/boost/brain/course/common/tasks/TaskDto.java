package boost.brain.course.common.tasks;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskDto {
    @ApiModelProperty(notes = "The database generated product ID",example = "45",position=1)
    private long id;
    @ApiModelProperty(notes = "Номер проекта",example = "19",position=2)
    private int project;
    @ApiModelProperty(notes = "Email автора",example = "author@mail.me",position=3)
    private String author;
    @ApiModelProperty(notes = "Email исполнителя",example = "implementor@mail.me",position=4)
    private String implementer;
    @ApiModelProperty(notes = "Название Задания",example = "Название Задания",position=5)
    private String name;
    @ApiModelProperty(notes = "Текст Задания",example = "Текст Задания",position=6)
    private String text;
    @ApiModelProperty(notes = "Время создания Задания",example = "2356488",position=7)
    private long createDate;
    @ApiModelProperty(notes = "Время обновления Задания",example = "23985218",position=8)
    private long updateDate;

}