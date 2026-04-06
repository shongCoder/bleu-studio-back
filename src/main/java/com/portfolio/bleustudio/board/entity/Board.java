package com.portfolio.bleustudio.board.entity;

import com.portfolio.bleustudio.common.entity.BaseEntity;
import com.portfolio.bleustudio.manager.entity.Manager;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_no", nullable = false)
    private Manager manager;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "attachment_key")
    private String attachmentKey;
}