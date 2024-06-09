package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.Board;

import java.sql.Timestamp;

@Builder
public record BoardResponse(
        Long id,
        String title,
        Board category,
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
        BoardResponseBuilder builder = BoardResponse.builder();
        builder.id(board.getId());
        builder.title(board.getTitle());
        builder.category(board.getCategory());
        builder.user(UserResponse.from(board.getUser()));
        builder.content(board.getContent());
        builder.image1(board.getImage1());
        builder.image2(board.getImage2());
        builder.image3(board.getImage3());
        builder.image4(board.getImage4());
        builder.image5(board.getImage5());
        builder.url(board.getUrl());
        builder.score(board.getScore());
        builder.createdAt(board.getCreatedAt());
        return builder
                .build();
    }
}
