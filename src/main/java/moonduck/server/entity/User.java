package moonduck.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "users", schema = "myschema", uniqueConstraints = @UniqueConstraint(columnNames = "nickname"))
public class User extends BaseEntity{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("기기 id")
    @Column(nullable = false, name = "device_id")
    private String deviceId;

    @Comment("유저닉네임")
    @Column(length = 10, unique = true)
    private String nickname;

//    @Comment("유저이름")
//    @Column(length = 10, nullable = false)
//    private String name;
//
//    @Comment("유저이메일")
//    @Column(nullable = false)
//    private String email;
}
