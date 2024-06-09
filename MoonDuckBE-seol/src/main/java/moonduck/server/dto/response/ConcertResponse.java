package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.program.Book;
import moonduck.server.entity.program.Concert;

@Builder
public record ConcertResponse(
        String thema,
        String place,
        String date,
        String actors,
        int price
) {
    public static ConcertResponse from(Concert concert) {
        return ConcertResponse.builder()
                .thema(concert.getThema())
                .place(concert.getPlace())
                .date(concert.getDate())
                .actors(concert.getActors())
                .price(concert.getPrice())
                .build();
    }
}
