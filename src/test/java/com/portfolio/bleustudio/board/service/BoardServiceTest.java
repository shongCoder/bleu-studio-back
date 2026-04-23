package com.portfolio.bleustudio.board.service;

import com.portfolio.bleustudio.board.dto.BoardRequestDto;
import com.portfolio.bleustudio.board.entity.Board;
import com.portfolio.bleustudio.board.repository.BoardRepository;
import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;
import com.portfolio.bleustudio.manager.entity.Manager;
import com.portfolio.bleustudio.manager.repository.ManagerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    @DisplayName("게시판 등록 - 성공")
    void createBoard_savesBoardAndReturnsBoardNo() {
        BoardRequestDto requestDto = new BoardRequestDto();
        requestDto.setTitle("temp title");
        requestDto.setContent("temp content");

        Manager manager = Manager.builder()
                .managerNo(1L)
                .loginId("manager")
                .password("encoded-password")
                .name("admin")
                .build();

        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));
        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> {
            Board board = invocation.getArgument(0);
            ReflectionTestUtils.setField(board, "boardNo", 10L);
            return board;
        });

        Long boardNo = boardService.createBoard(requestDto);

        ArgumentCaptor<Board> boardCaptor = ArgumentCaptor.forClass(Board.class);
        verify(boardRepository).save(boardCaptor.capture());

        Board savedBoard = boardCaptor.getValue();
        assertThat(boardNo).isEqualTo(10L);
        assertThat(savedBoard.getManager()).isEqualTo(manager);
        assertThat(savedBoard.getTitle()).isEqualTo("temp title");
        assertThat(savedBoard.getContent()).isEqualTo("temp content");
    }

    @Test
    @DisplayName("게시판 등록 - 관리자 없는 경우 실패")
    void createBoard_throwsIllegalStateExceptionWhenManagerDoesNotExist() {
        BoardRequestDto requestDto = new BoardRequestDto();
        requestDto.setTitle("temp title");
        requestDto.setContent("temp content");

        when(managerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boardService.createBoard(requestDto))
                .isInstanceOf(RestApiException.class)
                .extracting("errorEnum")
                .isEqualTo(ErrorEnum.MANAGER_NOT_FOUND);
    }
}
