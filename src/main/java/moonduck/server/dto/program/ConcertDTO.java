package moonduck.server.dto.program;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertDTO extends ProgramDTO{
    private String place;
    private String actor;
    private String price;
}
