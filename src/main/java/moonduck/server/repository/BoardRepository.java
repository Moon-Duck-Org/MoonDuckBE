package moonduck.server.repository;

import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
