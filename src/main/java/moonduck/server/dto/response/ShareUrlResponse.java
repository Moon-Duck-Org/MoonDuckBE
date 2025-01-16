package moonduck.server.dto.response;

import lombok.Builder;

@Builder
public record ShareUrlResponse(String url) {
}
