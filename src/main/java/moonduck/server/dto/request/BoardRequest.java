package moonduck.server.dto.request;

import lombok.*;
import moonduck.server.enums.Category;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardRequest {
    private String title;
    private Category category;
    private String content;
    private String url;
    private Integer score;
}
