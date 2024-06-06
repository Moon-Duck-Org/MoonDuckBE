package moonduck.server.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
public class Movie extends Board{

    private String movieNm;
    private String openDt;
    private String genreNm;
    private String directors;
    private  String actors;


}
