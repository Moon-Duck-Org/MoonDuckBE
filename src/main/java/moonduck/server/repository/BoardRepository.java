package moonduck.server.repository;

import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import moonduck.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b left join fetch b.user where b.id = :id")
    Optional<Board> findByIdWithUser(@Param("id") Long id);

    List<Board> findByUserId(Long userId);

    List<Board> findByUserIdAndCategory(Long userId, Category category);
}
