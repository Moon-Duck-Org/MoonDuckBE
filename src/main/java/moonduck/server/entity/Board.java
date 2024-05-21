package moonduck.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "board", schema="myschema")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("제목")
    @Column(length = 30, nullable = false)
    private String title;

    @Comment("카테고리")
    @Column(nullable = false)
    private String category;

    @Comment("유저이름")
    @Column(length = 10, nullable = false)
    private String name;

    @Comment("유저닉네임")
    @Column(length = 10, nullable = false)
    private String nickname;

    @Comment("이미지")
    @Column
    private String image;

    @Comment("관련url저장")
    @Column
    private String url;

    @Comment("별점")
    @Column(nullable = false)
    private Integer score;

    @Comment("생성날짜")
    @Column
    @CreatedDate
    private LocalDateTime createtime;

    @Comment("수정날짜")
    @Column
    @CreatedDate
    private LocalDateTime modtime;

}
