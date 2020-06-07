package boost.brain.course.common.messages;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    @ApiModelProperty(notes = "The database generated product ID",example = "45",position=1)
    private long id;
    @ApiModelProperty(notes = "Email автора",example = "author@mail.me",position=2)
    private String author;
    @ApiModelProperty(notes = "Email получателя",example = "recipient@mail.me",position=3)
    private String recipient;
    @ApiModelProperty(notes = "Текст сообщения",example = "Привет. Когда будет готова таска по заданию?",position=4)
    private String text;
    @ApiModelProperty(notes = "Время создания сообщения",example = "2356488",position=5)
    private long createDate;
    @ApiModelProperty(notes = "Время редактирования сообщения",example = "23598998",position=6)
    private long editDate;
    @ApiModelProperty(notes = "Время получения сообщения",example = "23698998",position=7)
    private long readDate;
    @ApiModelProperty(notes = "Флажок редактирования сообщения",example = "true",position=8)
    private boolean edited;
    @ApiModelProperty(notes = "Флажок прочтения сообщения",example = "true",position=9)
    private boolean read;

}
