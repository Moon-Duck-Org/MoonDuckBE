package moonduck.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "myschema",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "nickname"),
            @UniqueConstraint(columnNames = "sns_id")
        })
@Schema(description = "유저 엔티티")
public class User extends BaseEntity{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("유저 sns 고유 id")
    @Column(nullable = false, name = "sns_id", unique = true)
    private String snsId;

    @Comment("유저닉네임")
    @Column(length = 10, unique = true)
    private String nickname;
}
