package moonduck.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonduck.server.entity.Category;

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
