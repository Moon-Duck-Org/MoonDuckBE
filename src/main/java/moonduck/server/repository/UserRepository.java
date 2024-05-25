package moonduck.server.repository;

import moonduck.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDeviceId(String deviceId);

    boolean existsByNickname(String nickname);

    @Query("SELECT b.category, COUNT(b) FROM Board b WHERE b.user.deviceId = :deviceId GROUP BY b.category")
    List<Object[]> countByCategoryAndUserId(@Param("deviceId") String deviceId);


//    boolean existsByEmail(String email);
//
//    Optional<User> findByEmail(String email);
}
