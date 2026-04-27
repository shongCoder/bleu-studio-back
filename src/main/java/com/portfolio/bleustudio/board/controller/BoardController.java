package com.portfolio.bleustudio.board.controller;

import com.portfolio.bleustudio.auth.annotation.AccessLevel;
import com.portfolio.bleustudio.auth.enums.AccessLevelEnum;
import com.portfolio.bleustudio.auth.enums.AuthLevel;
import com.portfolio.bleustudio.board.dto.BoardRequestDto;
import com.portfolio.bleustudio.board.service.BoardService;
import com.portfolio.bleustudio.common.dto.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 등록
     *
     * @param requestDto 제목, 내용
     * @param file       pdf, excel, jpg, png, hwp, word 등
     * @return 게시글 번호
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("")
    public ResponseDTO<Long> createBoard(@RequestPart("request") @Valid BoardRequestDto requestDto
                                        , @RequestPart(value = "board", required = false) List<MultipartFile> file
    ){

        Long boardNo = boardService.createBoard(requestDto);

        return ResponseDTO.success(boardNo, "게시물 등록 성공");
    }
}
