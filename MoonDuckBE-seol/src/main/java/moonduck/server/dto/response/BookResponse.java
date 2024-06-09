package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.program.Book;

@Builder
public record BookResponse(
        String thema,
        String author,
        String publisher,
        String date
) {
    public static BookResponse from(Book book) {
        return BookResponse.builder()
                .thema(book.getThema())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .date(book.getDate())
                .build();
    }
}
