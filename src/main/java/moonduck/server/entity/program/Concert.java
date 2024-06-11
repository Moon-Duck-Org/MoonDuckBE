package moonduck.server.entity.program;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @Column(nullable = false)
    private String place;

    @Comment("출연진")
    @Column(nullable = false)
    private String actor;

    @Comment("티켓 가격")
    @Column(nullable = false)
    private String price;

    public Concert(String title, String date, String place, String actor, String price) {
        super(title, date);
        this.place = place;
        this.actor = actor;
        this.price = price;
    }
}
