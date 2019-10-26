package boost.brain.course.common.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private long id;
    private String author;
    private String recipient;
    private String text;
    private long createDate;
    private long editDate;
    private long readDate;
    private boolean edited;
    private boolean read;

}
