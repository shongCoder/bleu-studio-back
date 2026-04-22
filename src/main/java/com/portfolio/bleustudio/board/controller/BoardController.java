package com.portfolio.bleustudio.board.controller;

import com.portfolio.bleustudio.board.dto.BoardRequestDto;
import com.portfolio.bleustudio.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<Long> createBoard(@Valid @RequestPart BoardRequestDto requestDto
            , @RequestPart(value = "board", required = false) List<MultipartFile> file
    ){

        Long boardNo = boardService.createBoard(requestDto);

        return ResponseEntity.ok(boardNo);
    }
}
