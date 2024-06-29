package moonduck.server.entity.program;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moonduck.server.dto.program.ConcertDTO;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("concert")
@Table(name = "concert", schema = "myschema")
@Schema(description = "공연 엔티티")
public class Concert extends Program{

    @Comment("공연 장소")
    private String place;

    @Comment("분류(장르)")
    private String genre;

    public Concert(String title, String date, String place, String genre) {
        super(title, date);
        this.place = place;
        this.genre = genre;
    }

    public Concert(ConcertDTO concertDTO) {
        super(concertDTO.getTitle(), concertDTO.getDate());
        this.place = concertDTO.getPlace();
        this.genre = concertDTO.getGenre();
    }
}
