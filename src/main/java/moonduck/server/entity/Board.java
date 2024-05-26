package moonduck.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import moonduck.server.dto.BoardEditDTO;
import moonduck.server.dto.BoardRequestDTO;
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
    @Column(length = 30, nullable = false)
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
        this.image1 = boardDto.getImage1();
        this.image2 = boardDto.getImage2();
        this.image3 = boardDto.getImage3();
        this.image4 = boardDto.getImage4();
        this.image5 = boardDto.getImage5();
        this.url = boardDto.getUrl();
        this.score = boardDto.getScore();
    }
}
