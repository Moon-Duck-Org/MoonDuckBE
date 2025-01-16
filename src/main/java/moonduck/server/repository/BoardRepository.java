package moonduck.server.repository;

import moonduck.server.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b left join fetch b.user left join fetch b.program where b.id = :id")
    Optional<Board> findByIdWithUserAndProgram(@Param("id") Long id);

    @Query("select b from Board b left join fetch b.user where b.user.id = :uid")
    List<Board> findAllByUserId(@Param("uid") Long userId); //TODO: uid 부분이랑 userId부분 구분하여 정리하기

    @Query("select b from Board b where b.id = :id and b.user.id = :userId")
    Optional<Board> findByIdAndUserId(@Param("id") Long boardId, @Param("userId") Long userId);

    @Query("select b from Board b left join fetch b.program where b.id = :id")
    Optional<Board> findByIdWithProgram(@Param("id") Long id);

//    @Query("select b from Board b left join fetch b.user where b.user.id = :userId")
//    List<Board> findByUserId(@Param("userId") Long userId);

//    @Query("select b from Board b left join fetch b.user where b.user.id = :userId and b.category = :category")
//    List<Board> findByUserIdAndCategory(@Param("userId") Long userId, @Param("category") Category category);
}
