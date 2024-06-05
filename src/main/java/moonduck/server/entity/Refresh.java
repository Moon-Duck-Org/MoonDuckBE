package moonduck.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "myschema")
public class Refresh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String refresh;
    private String expiration;

    public Refresh(String userId, String refresh, Date expiration) {
        this.userId = userId;
        this.refresh = refresh;
        this.expiration = expiration.toString();
    }
}
