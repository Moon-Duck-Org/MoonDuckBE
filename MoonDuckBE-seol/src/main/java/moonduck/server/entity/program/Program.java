package moonduck.server.entity.program;

import jakarta.persistence.*;
import moonduck.server.entity.Board;
import moonduck.server.enums.Category;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "Program", schema = "myschema")
@DiscriminatorColumn(name = "Category")
public abstract class Program {

    @Id
    @Column(name = "program_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @ManyToMany(mappedBy = "category")
   // private List<Board> boards = new ArrayList<Board>();

    //==Getter Setter==//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   // public List<Board> getBoards() {
   //     return boards;
   // }

   // public void setBoards(List<Board> boards) {
   //     this.boards = boards;
   // }

}