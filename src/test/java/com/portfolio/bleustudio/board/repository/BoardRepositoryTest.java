package com.portfolio.bleustudio.board.repository;

import com.portfolio.bleustudio.board.entity.Board;
import com.portfolio.bleustudio.manager.entity.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("게시글 저장 성공")
    void save() {
        Manager manager = createManager();

        Board board = Board.builder()
                .manager(manager)
                .title("test title")
                .content("test content")
                .attachmentKey("board-attachment-key")
                .build();

        Board savedBoard = boardRepository.saveAndFlush(board);

        assertThat(savedBoard.getBoardNo()).isNotNull();
        assertThat(savedBoard.getTitle()).isEqualTo("test title");
        assertThat(savedBoard.getContent()).isEqualTo("test content");
        assertThat(savedBoard.getAttachmentKey()).isEqualTo("board-attachment-key");
        assertThat(savedBoard.getManager().getManagerNo()).isEqualTo(manager.getManagerNo());
    }

    @Test
    @DisplayName("게시글 단건 조회 성공")
    void findById() {
        Manager manager = createManager();

        Board board = Board.builder()
                .manager(manager)
                .title("find title")
                .content("find content")
                .build();

        Board savedBoard = boardRepository.saveAndFlush(board);
        entityManager.clear();

        Optional<Board> result = boardRepository.findById(savedBoard.getBoardNo());

        assertThat(result).isPresent();
        assertThat(result.get().getBoardNo()).isEqualTo(savedBoard.getBoardNo());
        assertThat(result.get().getTitle()).isEqualTo("find title");
        assertThat(result.get().getContent()).isEqualTo("find content");
    }

    private Manager createManager() {
        Manager manager = Manager.builder()
                .loginId("manager-" + System.nanoTime())
                .password("encoded-password")
                .name("admin")
                .phone("01012345678")
                .email("manager-" + System.nanoTime() + "@test.com")
                .useState(true)
                .build();

        entityManager.persist(manager);
        entityManager.flush();
        return manager;
    }
}
