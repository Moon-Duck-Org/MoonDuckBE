package moonduck.server.entity.program;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @Column(nullable = false)
    private String genre;

    @Comment("감독")
    @Column(nullable = false)
    private String director;

    @Comment("배우")
    @Column(nullable = false)
    private String actor;

    public Movie(String title, String date, String genre, String director, String actor) {
        super(title, date);
        this.genre = genre;
        this.director = director;
        this.actor = actor;
    }
}
