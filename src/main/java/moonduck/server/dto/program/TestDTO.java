package moonduck.server.dto.program;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO extends ProgramDTO{
    private String genre;
    private String director;
    private String actor;
    private String publisher;
    private String place;
    private String price;
}
