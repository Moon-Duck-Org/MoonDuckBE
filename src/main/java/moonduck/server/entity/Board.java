package moonduck.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import moonduck.server.dto.request.BoardEditRequest;
import moonduck.server.dto.request.BoardRequest;
import moonduck.server.entity.program.Program;
import moonduck.server.enums.Category;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board", schema = "myschema")
@Schema(description = "리뷰 엔티티")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Comment("제목")
    @Column(nullable = false)
    private String title;

    @Comment("카테고리")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Comment("유저 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Schema(description = "유저 정보")
    private User user;

    @Comment("프로그램 정보")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "program_id")
    @Schema(description = "프로그램 정보")
    private Program program;

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

    public Board(BoardRequest boardDto) {
        this.title = boardDto.getTitle();
        this.category = boardDto.getCategory();
        this.content = boardDto.getContent();
        this.url = boardDto.getUrl();
        this.score = boardDto.getScore();
    }

    public void updateBoard(BoardEditRequest boardDto) {
        this.title = boardDto.getTitle();
        this.category = boardDto.getCategory();
        this.content = boardDto.getContent();
        this.url = boardDto.getUrl();
        this.score = boardDto.getScore();
    }
}
