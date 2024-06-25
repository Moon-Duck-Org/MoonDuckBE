package moonduck.server.entity.item;

import jakarta.persistence.Entity;
import moonduck.server.entity.Board;

@Entity
public class Movie extends Board {

    private String movieNm;
    private String openDt;
    private String genreNm;
    private String directors;
    private  String actors;

    public String getMovieNm() {
        return movieNm;
    }

    public void setMovieNm(String movieNm) {
        this.movieNm = movieNm;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getGenreNm() {
        return genreNm;
    }

    public void setGenreNm(String genreNm) {
        this.genreNm = genreNm;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "Movie{}";
    }
}