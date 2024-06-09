package moonduck.server.entity.program;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import moonduck.server.entity.Board;
import org.hibernate.annotations.Comment;

@Entity
@DiscriminatorValue("DRAMA")
public class Drama extends Program {

    @Comment("상품 제목")
    private String thema;

    @Comment("시작 년도")
    private String startyear;

    @Comment("장르")
    private String genre;

    @Comment("감독")
    private String director;

    @Comment("배우")
    private String actors;

    public String getThema() {
        return thema;
    }

    public void setThema(String title) {
        this.thema = thema;
    }

    public String getStartyear() {
        return startyear;
    }

    public void setStartyear(String startyear) {
        this.startyear = startyear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return  "Drama{" +
                "thema='" + thema + '\'' +
                ", startyear ='" + startyear + '\'' +
                ", genre ='" + genre + '\'' +
                ", director ='" + director + '\'' +
                ", actors ='" + actors + '\'' +
                '}';
    }
}
