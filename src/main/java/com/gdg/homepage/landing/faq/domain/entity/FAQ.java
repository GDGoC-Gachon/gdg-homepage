package com.gdg.homepage.landing.faq.domain.entity;

import com.gdg.homepage.landing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class FAQ extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private String answer;

    /// 정적 팩토리 메서드
    public static FAQ of(String question, String answer) {
        return FAQ.builder()
                .question(question)
                .answer(answer)
                .build();
    }

    // 질문 수정 메소드
    public void updateQuestion(String question) {
        if (question != null) {
            this.question = question;
        }
    }

    // 답변 수정 메소드
    public void updateAnswer(String answer) {
        if (answer != null) {
            this.answer = answer;
        }
    }
}
