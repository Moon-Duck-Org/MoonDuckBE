package moonduck.server.repository.redis;

import moonduck.server.entity.Share;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends CrudRepository<Share, String> {
}
