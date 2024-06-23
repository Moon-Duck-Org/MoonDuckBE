package moonduck.server.entity.program;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moonduck.server.dto.program.TestDTO;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("test")
@Table(name = "test", schema = "myschema")
@Schema(description = "program 테스트 엔티티")
public class Test extends Program{

    @Comment("장르")
    private String genre;

    @Comment("감독")
    private String director;

    @Comment("배우")
    private String actor;

    @Comment("출판사(영화사)")
    private String publisher;

    @Comment("장소")
    private String place;

    @Comment("가격")
    private String price;

    public Test(String title, String date, String genre, String director, String actor, String publisher, String place, String price) {
        super(title, date);
        this.genre = genre;
        this.director = director;
        this.actor = actor;
        this.publisher = publisher;
        this.place = place;
        this.price = price;
    }

    public Test(TestDTO testDTO) {
        super(testDTO.getTitle(), testDTO.getDate());
        this.genre = testDTO.getGenre();
        this.director = testDTO.getDirector();
        this.actor = testDTO.getActor();
        this.publisher = testDTO.getPublisher();
        this.place = testDTO.getPlace();
        this.price = testDTO.getPrice();
    }
}
