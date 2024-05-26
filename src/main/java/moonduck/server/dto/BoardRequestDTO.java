package moonduck.server.dto;

import lombok.*;
import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import moonduck.server.entity.User;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardRequestDTO {
    private String title;
    private Category category;
    private String content;
    private String url;
    private Integer score;
    private Long userId;
}
