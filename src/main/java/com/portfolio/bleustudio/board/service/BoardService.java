package com.portfolio.bleustudio.board.service;

import com.portfolio.bleustudio.board.dto.BoardRequestDto;
import com.portfolio.bleustudio.board.entity.Board;
import com.portfolio.bleustudio.board.repository.BoardRepository;
import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;
import com.portfolio.bleustudio.manager.entity.Manager;
import com.portfolio.bleustudio.manager.repository.ManagerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class BoardService {

    private final BoardRepository boardRepository;
    private final ManagerRepository managerRepository;

    @Transactional
    public Long createBoard(@Valid BoardRequestDto requestDto) {

        Manager manager = managerRepository.findById(1L)
                .orElseThrow(() -> new RestApiException(ErrorEnum.MANAGER_NOT_FOUND));

        Board board = Board.builder()
                .manager(manager)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        Board savedBoard = boardRepository.save(board);

        return savedBoard.getBoardNo();
    }
}
