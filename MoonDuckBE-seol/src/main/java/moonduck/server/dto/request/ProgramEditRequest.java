package moonduck.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProgramEditRequest{
    //카테고리별 정보
    private String thema;
    private String author;
    private String publisher;
    private String place;
    private String date;
    private String actors;
    private int price;
    private String startyear;
    private String genre;
    private String director;
    private Long programId;
}
