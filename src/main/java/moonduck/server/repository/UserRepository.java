package moonduck.server.repository;

import moonduck.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDeviceId(String deviceId);

    boolean existsByNickname(String nickname);

//    boolean existsByEmail(String email);
//
//    Optional<User> findByEmail(String email);
}
