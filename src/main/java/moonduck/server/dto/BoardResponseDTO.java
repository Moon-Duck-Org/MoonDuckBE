package moonduck.server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moonduck.server.entity.Category;
import moonduck.server.entity.User;

@Data
@NoArgsConstructor
public class BoardResponseDTO {

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

    @Builder
    public BoardResponseDTO(String title, Category category, User nickname,
                            User user, String content, String image1, String image2, String image3, String s, String image4, String image5, String url, Integer score)
    {
        this.user = this.user;
        this.title = title;
        this.category = this.category;
        this.content = content;
        this.image1 = this.image1;
        this.image2 = this.image2;
        this.image3 = this.image3;
        this.image4 = this.image4;
        this.image5 = this.image5;
        this.url = this.url;
        this.score = this.score;
    }
}