package moonduck.server.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.controller.BoardAPI;
import moonduck.server.dto.request.BoardEditRequest;
import moonduck.server.dto.request.BoardRequest;
import moonduck.server.entity.Board;
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
    public ResponseEntity<Board> savePost(MultipartFile[] images, BoardRequest boardDto, Long userId) {
        List<String> imageFiles = s3Service.uploadFiles(images, userId);
        Board board = boardService.savePost(imageFiles, boardDto, userId);

        return ResponseEntity.ok(board);
    }


    //Update 수정
    @Override
    public ResponseEntity<Board> updatePost(MultipartFile[] images, BoardEditRequest boardDto, Long userId) {
        List<String> imageFiles = (images == null ? new ArrayList<>() : s3Service.uploadFiles(images, userId));
        Board editedBoard = boardService.update(imageFiles, boardDto);

        return ResponseEntity.ok(editedBoard);
    }

    //Read
    @Override
    public ResponseEntity<Page<Board>> findPosts(Long userId, String filter, int offset, int size){
        Pageable pageable = PageRequest.of(offset, size);
        Page<Board> reviews = boardService.getAllReview(userId, filter, pageable);

        return ResponseEntity.ok(reviews);
    }

    // 카테고리별 리스트 조회
    @Override
    public ResponseEntity<Page<Board>> search(Long userId, String category, String filter, int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size);
        Page<Board> reviewWithCategory = boardService.getReviewWithCategory(userId, category, filter, pageable);

        return ResponseEntity.ok(reviewWithCategory);
    }



    //Read
    @Override
    public ResponseEntity<Board> findPost(Long boardId){
        Board review = boardService.getReview(boardId);

        return ResponseEntity.ok(review);
    }


    //Delete
    @Override
    public ResponseEntity<Boolean> delete(Long boardId){
        boardService.deletePost(boardId);

        return ResponseEntity.ok(true);
    }
}