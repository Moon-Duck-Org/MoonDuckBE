package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.program.Drama;
import moonduck.server.entity.program.Movie;

@Builder
public record MovieResponse (
        String thema,
        String date,
        String genre,
        String director,
        String actors
) {
        public static MovieResponse from(Movie movie) {
            return MovieResponse.builder()
                    .thema(movie.getThema())
                    .date(movie.getDate())
                    .genre(movie.getGenre())
                    .director(movie.getDirector())
                    .actors(movie.getActors())
                    .build();
        }
}