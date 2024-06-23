package moonduck.server.dto.program;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "program_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MovieDTO.class, name = "MOVIE"),
        @JsonSubTypes.Type(value = BookDTO.class, name = "BOOK"),
        @JsonSubTypes.Type(value = DramaDTO.class, name = "DRAMA"),
        @JsonSubTypes.Type(value = ConcertDTO.class, name = "CONCERT"),
        @JsonSubTypes.Type(value = TestDTO.class, name = "TEST")
})
public class ProgramDTO {

    private String title;
    private String date;
}
