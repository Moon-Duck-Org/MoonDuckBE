package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.program.Program;

@Builder
public record ShareDataResponse(
        String title,
        String category,
        Program program,
        String content,
        String image,
        Integer score,
        String createdAt
) {
}
