package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.User;
import moonduck.server.entity.program.Program;

@Builder
public record ProgramResponse(
        Long programId
) {
    public static ProgramResponse from(Program program) {
        return ProgramResponse.builder()
                .programId(program.getId())
                .build();
    }
}
