package com.portfolio.bleustudio.board.repository;

import com.portfolio.bleustudio.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}
