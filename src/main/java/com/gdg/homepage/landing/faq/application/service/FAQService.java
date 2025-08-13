package com.gdg.homepage.landing.faq.application.service;

import com.gdg.homepage.core.response.ErrorCode;
import com.gdg.homepage.landing.faq.application.dto.request.FAQRequest;
import com.gdg.homepage.landing.faq.application.dto.request.FAQUpdateRequest;
import com.gdg.homepage.landing.faq.application.dto.response.FAQResponse;
import com.gdg.homepage.landing.faq.application.usecase.FAQUseCase;
import com.gdg.homepage.landing.faq.domain.entity.FAQ;
import com.gdg.homepage.landing.faq.domain.repository.FAQRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class FAQService implements FAQUseCase {

    private final FAQRepository repository;


    /**
     * FAQ 생성
     * @param faqRequest        요청 DTO
     */
    public FAQResponse createFAQ(FAQRequest faqRequest) {

        /// 객체 생성
        FAQ faq = FAQ.of(faqRequest.getQuestion(), faqRequest.getAnswer());

        /// DB 저장
        FAQ savedFaq = repository.save(faq);

        /// DTO 변환
        return FAQResponse.from(savedFaq);
    }

    /**
     * FAQ 수정
     * @param request    수정 DTO
     */
    public FAQResponse updateFAQ(FAQUpdateRequest request) {

        /// DB 조회
        FAQ faq = repository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND_END_POINT.getMessage()));

        /// 요청 값이 존재하면 수정
        if(request.getQuestion()!=null) {
            faq.updateQuestion(request.getQuestion());
        }
        if(request.getAnswer()!=null){
            faq.updateAnswer(request.getAnswer());
        }

        /// 저장
        FAQ updateFAQ = repository.save(faq);

        /// DTO 변환
        return FAQResponse.from(updateFAQ);
    }

    /**
     * FAQ 삭제
     * @param id    삭제할 ID
     */
    public void deleteFAQ(Long id) {

        repository.deleteById(id);
    }


    /**
     * FAQ 목록 조회
     * @return
     */
    public List<FAQResponse> getAllFAQs() {

        /// DB 호출
        List<FAQ> faqs = repository.findAll();

        /// 리턴
        return FAQResponse.from(faqs);
    }
}
