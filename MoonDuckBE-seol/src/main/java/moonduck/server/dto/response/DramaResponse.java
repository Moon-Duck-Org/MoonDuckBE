package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.program.Drama;

@Builder
public record DramaResponse(
        String thema,
        String startyear,
        String genre,
        String director,
        String actors
) {
    public static DramaResponse from(Drama drama) {
        return DramaResponse.builder()
                .thema(drama.getThema())
                .startyear(drama.getStartyear())
                .genre(drama.getGenre())
                .director(drama.getDirector())
                .actors(drama.getActors())
                .build();
    }
}
