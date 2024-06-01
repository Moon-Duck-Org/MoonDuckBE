package moonduck.server.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class MovieList {
    @Getter
    @Setter
    static public class MovieListRequest{
        private String curPage;
        private String itemPerPage;
        private String movieNm;
        private String directorNm;
        private String openStartDt;
        private String openEndDt;
        private String prdtStartYear;
        private String prdtEndYear;
        private String repNationCd;
        private String movieTypeCd;
    }

    @Getter
    @Setter
    static public class MovieListResponse{
        private MovieListResult movieListResult;
    }

    @Getter
    @Setter
    static public class  MovieListResult{
        private int totCnt;
        private String source;
        private ArrayList<Movie> movieList;
    }

    @Getter
    @Setter
    static public class Movie
    {
        private String movieCd;
        private String movieNm;
        private String movieNmEn;
        private String prdtYear;
        private String openDt;
        private String typeNm;
        private String prdStatNm;
        private String nationAlt;
        private String genreAlt;
        private String repNationNm;
        private String repGenreNm;
        private ArrayList<Director> directors;
        private ArrayList<Company> companies;
    }

    @Getter
    @Setter
    static public class Director{
        private String peopleNm;
    }

    @Getter
    @Setter
    static public class Company{
        private String companyCd;
        private String companyNm;
    }
}
