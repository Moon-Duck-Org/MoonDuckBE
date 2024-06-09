package moonduck.server.entity.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonduck.server.entity.BaseEntity;
import moonduck.server.entity.Category;
import moonduck.server.entity.User;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board", schema = "myschema")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "category")
@Schema(description = "리뷰 엔티티")
public abstract class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Comment("제목")
    @Column(length = 30, nullable = false)
    private String title;

    @Comment("유저 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Schema(description = "유저 정보")
    private User user;

    @Comment("내용")
    @Column(columnDefinition = "TEXT")
    private String content;

    @Comment("이미지1")
    @Column
    private String image1;

    @Comment("이미지2")
    @Column
    private String image2;

    @Comment("이미지3")
    @Column
    private String image3;

    @Comment("이미지4")
    @Column
    private String image4;

    @Comment("이미지5")
    @Column
    private String image5;

    @Comment("관련url저장")
    @Column
    private String url;

    @Comment("별점")
    @Column(nullable = false)
    private Integer score;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();
    }


    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Board(BoardRequestDTO boardDto) {
        this.title = boardDto.getTitle();
        this.category = boardDto.getCategory();
        this.content = boardDto.getContent();
        this.url = boardDto.getUrl();
        this.score = boardDto.getScore();
    }

    public void updateBoard(BoardEditDTO boardDto) {
        this.title = boardDto.getTitle();
        this.category = boardDto.getCategory();
        this.content = boardDto.getContent();
        this.url = boardDto.getUrl();
        this.score = boardDto.getScore();
    }
}
