package moonduck.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import moonduck.server.entity.User;

import java.sql.Timestamp;

@Setter
@NoArgsConstructor
@Getter
public class BoardRequestDTO {

    private Long board_id;
    private String title;
    private Category category;
    private User nickname;
    private User user;
    private String content;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String url;
    private Integer score;
    private java.sql.Timestamp createdAt;
    private Timestamp modifiedAt;


    @Builder
    public BoardRequestDTO(String title, Category category, User nickname,
                           User user, String content, String image1, String image2, String image3, String image4,
                           String image5, String url, Integer score, java.sql.Timestamp createdAt, java.sql.Timestamp modifiedAt)
    {
        this.user = user;
        this.title = title;
        this.category = category;
        this.nickname = nickname;
        this.content = content;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.url = url;
        this.score = score;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Board ToEntity(){
        return Board.builder()
                .user(this.user)
                .title(this.title)
                .category(this.category)
                .nickname(this.nickname)
                .content(this.content)
                .image1(this.image1)
                .image2(this.image2)
                .image3(this.image3)
                .image4(this.image4)
                .image5(this.image5)
                .url(this.url)
                .score(this.score)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }
}
