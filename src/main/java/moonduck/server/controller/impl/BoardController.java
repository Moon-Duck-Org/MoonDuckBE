package moonduck.server.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.controller.BoardAPI;
import moonduck.server.dto.request.BoardEditRequest;
import moonduck.server.dto.request.BoardRequest;
import moonduck.server.dto.response.BoardResponse;
import moonduck.server.service.s3.S3Service;
import moonduck.server.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController implements BoardAPI {

    private final BoardService boardService;
    private final S3Service s3Service;

    //Create 생성
    @Override
    public ResponseEntity<BoardResponse> savePost(MultipartFile[] images, BoardRequest boardDto, Long userId) {
        List<String> imageFiles = s3Service.uploadFiles(images, userId);
        BoardResponse board = boardService.savePost(imageFiles, boardDto, userId);

        return ResponseEntity.ok(board);
    }


    //Update 수정
    @Override
    public ResponseEntity<BoardResponse> updatePost(MultipartFile[] images, BoardEditRequest boardDto, Long userId) {
        List<String> imageFiles = (images == null ? new ArrayList<>() : s3Service.uploadFiles(images, userId));
        BoardResponse editedBoard = boardService.update(imageFiles, boardDto);

        return ResponseEntity.ok(editedBoard);
    }

    //Read
    @Override
    public ResponseEntity<Page<BoardResponse>> findPosts(Long userId, String filter, int offset, int size){
        Pageable pageable = PageRequest.of(offset, size);
        Page<BoardResponse> reviews = boardService.getAllReview(userId, filter, pageable);

        return ResponseEntity.ok(reviews);
    }

    // 카테고리별 리스트 조회
    @Override
    public ResponseEntity<Page<BoardResponse>> search(Long userId, String category, String filter, int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size);
        Page<BoardResponse> reviewWithCategory = boardService.getReviewWithCategory(userId, category, filter, pageable);

        return ResponseEntity.ok(reviewWithCategory);
    }



    //Read
    @Override
    public ResponseEntity<BoardResponse> findPost(Long boardId){
        BoardResponse review = boardService.getReview(boardId);

        return ResponseEntity.ok(review);
    }


    //Delete
    @Override
    public ResponseEntity<Boolean> delete(Long boardId){
        boardService.deletePost(boardId);

        return ResponseEntity.ok(true);
    }
}