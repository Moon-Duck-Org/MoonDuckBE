package moonduck.server.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.controller.ShareAPI;
import moonduck.server.dto.response.ShareDataResponse;
import moonduck.server.dto.response.ShareUrlResponse;
import moonduck.server.service.ShareService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
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

    @Override
    public String getSharePage(String param, Model model) {
        ShareDataResponse shareData = shareService.getShareData(param);

        if (shareData == null) {
            return "error";
        }

        model.addAttribute("data", shareData);

        return "share";
    }

}
