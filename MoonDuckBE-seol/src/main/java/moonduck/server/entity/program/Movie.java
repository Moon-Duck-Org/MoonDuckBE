package moonduck.server.entity.program;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("MOVIE")
public class Movie extends Program {

    private String title;
    private String openDt;
    private String genre;
    private String director;
    private  String actors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
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
                "title='" + title + '\'' +
                ", openDt ='" + openDt + '\'' +
                ", genre ='" + genre + '\'' +
                ", director ='" + director + '\'' +
                ", actors ='" + actors + '\'' +
                '}';
    }
}