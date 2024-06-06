package moonduck.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "myschema")
public class Refresh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String refresh;
    private String expiration;

    public Refresh(Long userId, String refresh, Date expiration) {
        this.userId = userId;
        this.refresh = refresh;
        this.expiration = expiration.toString();
    }

    public void changeToken(String refreshToken, Date expiration) {
        this.refresh = refreshToken;
        this.expiration = expiration.toString();
    }
}
