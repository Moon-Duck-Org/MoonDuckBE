package moonduck.server.entity.program;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "program_type")
@Table(name = "program", schema = "myschema")
@Schema(description = "참고 프로그램 엔티티")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;

    @Comment("프로그램 제목")
    @Column(nullable = false)
    private String title;

    @Comment("개봉(출판) 날짜")
    private String date;

    public Program(String title, String date) {
        this.title = title;
        this.date = date;
    }
}
