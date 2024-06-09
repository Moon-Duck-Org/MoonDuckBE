package moonduck.server.entity.program;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

@Entity
@DiscriminatorValue("MOVIE")
public class Movie extends Program {

    @Comment("상품 제목")
    private String thema;

    @Comment("오픈 날짜")
    private String date;

    @Comment("장르")
    private String genre;

    @Comment("감독")
    private String director;

    @Comment("배우")
    private  String actors;

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        return  "Movie{" +
                "thema ='" + thema + '\'' +
                ", date ='" + date + '\'' +
                ", genre ='" + genre + '\'' +
                ", director ='" + director + '\'' +
                ", actors ='" + actors + '\'' +
                '}';
    }
}