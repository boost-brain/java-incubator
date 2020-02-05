package boost.brain.course.common.tasks;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentDto {
    @ApiModelProperty(notes = "The database generated product ID",example = "45",position=1)
    private long id;
    @ApiModelProperty(notes = "ID комментируемого задания TaskDto",example = "12",position=2)
    private long taskId;
    @ApiModelProperty(notes = "Email автора",example = "author@mail.me",position=3)
    private String author;
    @ApiModelProperty(notes = "Текст комментария",example = "Отличное задание. Всё сделано в срок." ,position=4)
    private String text;
    @ApiModelProperty(notes = "Время создания комментария",example = "2356488",position=5)
    private long createDate;
    @ApiModelProperty(notes = "Время обновления комментария",example = "23985218",position=6)
    private long updateDate;
}
