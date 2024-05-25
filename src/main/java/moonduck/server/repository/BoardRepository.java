package moonduck.server.repository;

import moonduck.server.entity.Board;
import moonduck.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<User> findByCategory(String category);

}
