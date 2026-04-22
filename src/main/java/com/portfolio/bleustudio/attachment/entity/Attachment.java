package com.portfolio.bleustudio.attachment.entity;

import com.portfolio.bleustudio.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attachment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Attachment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_no")
    private Long attachmentNo;

    /** 파일 그룹 키 (같은 게시글/문의 등 묶음) */
    @Column(name = "attachment_key", length = 100, nullable = false)
    private String attachmentKey;

    @Column(name = "category", length = 50, nullable = false)
    private String category;

    @Column(name = "original_name", length = 255, nullable = false)
    private String originalName;

    @Column(name = "stored_name", length = 255, nullable = false)
    private String storedName;

    @Column(name = "file_path", length = 512, nullable = false)
    private String filePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "extention", length = 100, nullable = false)
    private String extention;
}