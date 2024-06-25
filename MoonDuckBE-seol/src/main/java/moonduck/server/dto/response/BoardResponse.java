package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.Board;
import moonduck.server.enums.Category;

import java.sql.Timestamp;

@Builder
public record BoardResponse(
        Long id,
        String title,
        Category category,
        UserResponse user,
        String content,
        String image1,
        String image2,
        String image3,
        String image4,
        String image5,
        String url,
        Integer score,
        Timestamp createdAt
) {
    public static BoardResponse from(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .category(board.getCategory())
                .user(UserResponse.from(board.getUser()))
                .content(board.getContent())
                .image1(board.getImage1())
                .image2(board.getImage2())
                .image3(board.getImage3())
                .image4(board.getImage4())
                .image5(board.getImage5())
                .url(board.getUrl())
                .score(board.getScore())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
