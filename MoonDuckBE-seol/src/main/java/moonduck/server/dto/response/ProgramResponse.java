package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.program.*;

@Builder
public record ProgramResponse(
        Long programId,
        String thema,
        String author,
        String publisher,
        String place,
        String date,
        String actors,
        int price,
        String startyear,
        String genre,
        String director
) {


    public static ProgramResponse from(Program program) {
        return null;
    }
}
