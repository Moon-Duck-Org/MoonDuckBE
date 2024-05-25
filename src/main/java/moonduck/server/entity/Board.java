package moonduck.server.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "board", schema = "myschema")
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
    @JoinColumn(name = "users")
    private User user;

    @Comment("내용")
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

    @Builder
    public Board(String title, Category category,
                 User user, String content, String image1, String image2, String image3, String image4,
                 String image5, String url, Integer score)
    {
        this.user = user;
        this.title = title;
        this.category = category;
        this.content = content;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.url = url;
        this.score = score;
    }


    @Autowired
    public void updateBoard(String title, Category category, User nickname, User user_id,
                            String content, String image1, String image2, String image3, String image4, String image5, String url, Integer score) {
    }


}
