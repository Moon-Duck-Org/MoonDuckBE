package moonduck.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonduck.server.dto.program.ProgramDTO;
import moonduck.server.enums.Category;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardEditRequest {
    private String title;
    private Category category;
    private ProgramDTO program;
    private String content;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String url;
    private Integer score;
    private Long boardId;
}
