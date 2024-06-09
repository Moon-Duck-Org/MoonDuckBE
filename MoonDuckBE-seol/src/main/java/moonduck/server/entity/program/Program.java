package moonduck.server.entity.program;

import jakarta.persistence.*;
import moonduck.server.dto.request.BoardEditRequest;
import moonduck.server.dto.request.BoardRequest;
import org.hibernate.annotations.Comment;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(schema = "myschema")
@DiscriminatorColumn(name = "CATEGORY")
public abstract class Program {

    @Id @GeneratedValue
    @Column(name = "program_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
