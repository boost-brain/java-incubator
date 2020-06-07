package boost.brain.course.common.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Session {
    @ApiModelProperty(notes = "ID сессии, создается через метод UUID.randomUUID()",example = "44e128a5-ac7a-4c9a-be4c-224b6bf81b20",position=1)
    private String sessionId;
    @ApiModelProperty(notes = "Время начала текущей сессии",example = "121333423423",position=2)
    private long startTime;
    @ApiModelProperty(notes = "Время окончания текущей сессии. После этого времени надо будет логиниться по новой.",example = "121335665615551",position=3)
    private long validTime;
}
