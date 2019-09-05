package boost.brain.course.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "register_table")
public class EmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private boolean confirmed;
    private String token;

}
