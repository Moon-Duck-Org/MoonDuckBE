package moonduck.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;


public enum Category {
    MOVIE,
    BOOK,
    DRAMA,
    CONCERT;

    @JsonIgnore
    @OneToMany(mappedBy = "board")
    private List<Board> boards = new ArrayList<>();
}
