package moonduck.server.entity.program;

import jakarta.persistence.Entity;

@Entity
public class Drama extends Program {
    private String title;
    private String startyear;
    private String genre;
    private String director;
    private String actors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return  "Book{" +
                "title='" + title + '\'' +
                ", startyear ='" + startyear + '\'' +
                ", genre ='" + genre + '\'' +
                ", director ='" + director + '\'' +
                ", actors ='" + actors + '\'' +
                '}';
    }
}
