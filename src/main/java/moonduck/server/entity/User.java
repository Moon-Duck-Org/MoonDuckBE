package moonduck.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    //마이페이지 유저 아이디, 닉네임 만 들고온다했으닝 그것만 간단하겡)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //pk값 !

    @Comment("기기 id")
    @Column(nullable = false, name = "device_id")
    private String deviceId;

    @Comment("유저닉네임")
    @Column(length = 10)
    private String nickname;

    @Comment("리뷰 목록")
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

//    @Comment("유저이름")
//    @Column(length = 10, nullable = false)
//    private String name;
//
//    @Comment("유저이메일")
//    @Column(nullable = false)
//    private String email;
}
