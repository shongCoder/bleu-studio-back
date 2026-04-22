package com.portfolio.bleustudio.contact.entity;

import com.portfolio.bleustudio.common.entity.BaseEntity;
import com.portfolio.bleustudio.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "contact")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_no")
    private Long contactNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_no")
    private Customer customer;


    /**
     * 비회원 정보
     */
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    /** 사업자명 */
    @Column(name = "business_name", length = 100)
    private String businessName;

    @Column(name = "guest_password", length = 255)
    private String guestPassword;


    /**
     * 문의 내용
     */
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;


    /** 최소 예산 */
    @Column(name = "budget_min", nullable = false)
    private Integer budgetMin;

    /** 최대 예산 */
    @Column(name = "budget_max", nullable = false)
    private Integer budgetMax;

    /** 레퍼런스 URL */
    @Column(name = "ref_url", columnDefinition = "TEXT")
    private String refUrl;

    /** 첨부파일 key */
    @Column(name = "attachment_key")
    private String attachmentKey;

    @OneToMany(mappedBy = "contact")
    private List<ContactAnswer> answers;


    public void changeGuestPassword(String encodedGuestPassword) {
        this.guestPassword = encodedGuestPassword;
    }
}