package moonduck.server.repository;

import moonduck.server.entity.program.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
