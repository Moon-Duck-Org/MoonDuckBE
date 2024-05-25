package moonduck.server.dto;

import lombok.*;
import moonduck.server.entity.Category;
import moonduck.server.entity.User;

import java.security.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO {

    private Long board_id;
    private String title;
    private Category category;
    private User user;
    private String content;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String url;
    private Integer score;

}