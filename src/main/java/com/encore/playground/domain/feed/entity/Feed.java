package com.encore.playground.domain.feed.entity;

import com.encore.playground.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 피드 글 구조를 위한 클래스 (엔티티)
 */
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만들어줌
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 만들어줌
@Table(name = "feed") // DB에 들어갈 테이블 이름
@Getter // 모든 필드의 getter 메소드를 자동으로 생성
@Builder // 빌더 패턴 클래스 생성
@Entity // JPA 엔티티임을 나타냄
public class Feed {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 글번호

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false) // 외래키 이름
    private Member member; // 작성자의 멤버 id

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate; // 작성일자

    @LastModifiedDate
    private LocalDateTime modifiedDate; // 수정일자

    // TODO: 좋아요 수를 Like 테이블에서 join하여 가져오는 작업 예정
    @ColumnDefault("0")
    private Integer likeCount; // 좋아요 수

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer viewCount; // 조회 수

    @Column(nullable = false, length = 1000)
    private String content; // 글 내용

    @Transactional
    public void readFeed() { // 조회수 증가
        this.viewCount++;
    }
}