package moonduck.server.repository;

import moonduck.server.dto.query.CategoryCountDTO;
import moonduck.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySnsId(String snsId);

    boolean existsByNickname(String nickname);

    @Query("SELECT new moonduck.server.dto.query.CategoryCountDTO(b.category, COUNT(b)) " +
            "FROM Board b WHERE b.user.id = :userId GROUP BY b.category")
    List<CategoryCountDTO> countByCategoryAndUserId(@Param("userId") Long userId);


//    boolean existsByEmail(String email);
//
//    Optional<User> findByEmail(String email);
}
