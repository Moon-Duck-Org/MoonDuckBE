package moonduck.server.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Drama extends Board {
    private String title;
    private String startyear;
    private String genre;
    private String director;
    private String actors;
}
