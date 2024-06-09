package moonduck.server.dto.request;

import lombok.*;
import moonduck.server.entity.Board;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardRequest {
    private String title;
    private Board category;
    private String content;
    private String url;
    private Integer score;
}
