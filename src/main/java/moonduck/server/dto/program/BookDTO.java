package moonduck.server.dto.program;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO extends ProgramDTO{
    private String author;
    private String publisher;
}
