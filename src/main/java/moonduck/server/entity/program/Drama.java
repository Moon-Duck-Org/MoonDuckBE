package moonduck.server.entity.program;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moonduck.server.dto.program.DramaDTO;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("drama")
@Table(name = "drama", schema = "myschema")
@Schema(description = "드라마 엔티티")
public class Drama extends Program{

    @Comment("장르")
    private String genre;

    @Comment("감독")
    private String director;

    @Comment("배우")
    private String actor;

    public Drama(String title, String date, String genre, String director, String actor) {
        super(title, date);
        this.genre = genre;
        this.director = director;
        this.actor = actor;
    }

    public Drama(DramaDTO dramaDTO) {
        super(dramaDTO.getTitle(), dramaDTO.getDate());
        this.genre = dramaDTO.getGenre();
        this.director = dramaDTO.getDirector();
        this.actor = dramaDTO.getActor();
    }
}
