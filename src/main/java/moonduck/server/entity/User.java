package moonduck.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "User", schema="myschema")
public class User {

    //마이페이지 유저 아이디, 닉네임 만 들고온다했으닝 그것만 간단하겡)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id; //pk값 !

    @Comment("유저이름")
    @Column(length = 10, nullable = false)
    private String name;

    @Comment("유저닉네임")
    @Column(length = 10, nullable = false)
    private String nickname;

    @Comment("유저이메일")
    @Column(nullable = false)
    private String email;

    @Comment("비번")
    @Column(nullable = false)
    private String password;

    @Comment("생성날짜")
    @Column
    @CreatedDate
    private LocalDateTime createtime;

    @Comment("수정날짜")
    @Column
    @CreatedDate
    private LocalDateTime modtime;
}
