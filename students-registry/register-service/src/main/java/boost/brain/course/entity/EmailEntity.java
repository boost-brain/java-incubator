package boost.brain.course.entity;

import lombok.Data;
import org.mapstruct.Named;

import javax.persistence.*;

@Entity
@Data
@Table(name = "register_table")
public class EmailEntity {

    @Id
    private String email;
    private String name;
    private boolean confirmed;
    private String token;

}
