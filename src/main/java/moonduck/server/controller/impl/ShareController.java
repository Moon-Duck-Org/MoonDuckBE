package moonduck.server.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.controller.ShareAPI;
import moonduck.server.dto.response.ShareUrlResponse;
import moonduck.server.service.ShareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ShareController implements ShareAPI {

    private final ShareService shareService;

    @Override
    public ResponseEntity<ShareUrlResponse> getShareUrl(Long userId, Long boardId) {
        String shareUrl = shareService.getShareUrl(userId, boardId);

        ShareUrlResponse urlResponse = ShareUrlResponse.builder()
                .url(shareUrl)
                .build();

        return ResponseEntity.ok(urlResponse);
    }
}
