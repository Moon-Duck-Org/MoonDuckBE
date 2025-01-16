package moonduck.server.entity.program;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moonduck.server.dto.program.MovieDTO;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("movie")
@Table(name = "movie", schema = "myschema")
@Schema(description = "영화 엔티티")
public class Movie extends Program{

    @Comment("장르")
    private String genre;

    @Comment("감독")
    private String director;

    public Movie(String title, String date, String genre, String director) {
        super(title, date);
        this.genre = genre;
        this.director = director;
    }

    public Movie(MovieDTO movieDTO) {
        super(movieDTO.getTitle(), movieDTO.getDate());
        this.genre = movieDTO.getGenre();
        this.director = movieDTO.getDirector();
    }

}
