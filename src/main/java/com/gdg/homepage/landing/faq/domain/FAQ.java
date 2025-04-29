package com.gdg.homepage.landing.faq.domain;

import com.gdg.homepage.common.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class FAQ extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private String answer;

    @Builder
    public FAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
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
