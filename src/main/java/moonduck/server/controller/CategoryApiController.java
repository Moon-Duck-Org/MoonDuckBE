package moonduck.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.entity.Board;
import moonduck.server.vo.BookVO;
import moonduck.server.vo.NaverResultVO;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.*;

import java.net.URI;

@Tag(name = "문덕 카테고리별 공공 API", description = "문덕 카테고리별 공공 API")
@ApiResponse(responseCode = "200", description = "OK")
@RequiredArgsConstructor
@RestController
@Slf4j
public class CategoryApiController {

    //Book Category
    @Controller
    public class BookApiController {

        @Operation(summary = "책 카테고리 api 검색", description = "리뷰 카테고리별 리스트를 가져옵니다.")
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Board.class))
        ))
        @GetMapping("/api/review/book")
        public String list(String text, Model model) {

            // 네이버 검색 API 요청
            String clientId = "icz58QRAGz0BJ5WTZHuL";
            String clientSecret = "NMvxQbJKDX";

            //String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // JSON 결과
            URI uri = UriComponentsBuilder
                    .fromUriString("https://openapi.naver.com")
                    .path("/v1/search/book.json")
                    .queryParam("query", text)
                    .queryParam("display", 10)
                    .queryParam("start", 1)
                    .queryParam("sort", "sim")
                    .encode()
                    .build()
                    .toUri();

            // Spring 요청 제공 클래스
            RequestEntity<Void> req = RequestEntity
                    .get(uri)
                    .header("X-Naver-Client-Id", clientId)
                    .header("X-Naver-Client-Secret", clientSecret)
                    .build();
            // Spring 제공 restTemplate
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

            // JSON 파싱 (Json 문자열을 객체로 만듦, 문서화)
            ObjectMapper om = new ObjectMapper();
            NaverResultVO resultVO = null;

            try {
                resultVO = om.readValue(resp.getBody(), NaverResultVO.class);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            List<BookVO> books =resultVO.getItems();	// books를 list.html에 출력 -> model 선언
            model.addAttribute("books", books);

            return "/api/review/book";
        }

    }


}
