package moonduck.server.repository;

import moonduck.server.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findAllByOrderByIdAsc();
}
