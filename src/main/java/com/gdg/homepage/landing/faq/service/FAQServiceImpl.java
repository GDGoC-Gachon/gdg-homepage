package com.gdg.homepage.landing.faq.service;

import com.gdg.homepage.common.response.CustomException;
import com.gdg.homepage.common.response.ErrorCode;
import com.gdg.homepage.landing.faq.dto.FAQRequest;
import com.gdg.homepage.landing.faq.dto.FAQResponse;
import com.gdg.homepage.landing.faq.domain.FAQ;
import com.gdg.homepage.landing.faq.repository.FAQRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;

    // FAQ 생성 - FAQResponse로 반환할 것이 없다면 void 처리할 예정
    public FAQResponse createFAQ(FAQRequest faqRequest) {
        FAQ faq = FAQ.builder()
                .question(faqRequest.getQuestion())
                .answer(faqRequest.getAnswer())
                .build();
        FAQ savedFaq = faqRepository.save(faq);
        return FAQResponse.from(savedFaq.getId(), savedFaq.getQuestion(), savedFaq.getAnswer());
    }

    // FAQ 수정 - 반환할게 없다면 void 처리/ 수정 요청값을 무조건 넣어
    public FAQResponse updateFAQ(Long id, FAQRequest faqRequest) {
        FAQ faq = faqRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_END_POINT));
        // 요청 값이 존재하면 수정
        if(faqRequest.getQuestion()!=null) {
            faq.updateQuestion(faqRequest.getQuestion());
        }
        if(faqRequest.getAnswer()!=null){
            faq.updateAnswer(faqRequest.getAnswer());
        }
        FAQ updatedFaq = faqRepository.save(faq);
        return FAQResponse.from(updatedFaq.getId(), updatedFaq.getQuestion(), updatedFaq.getAnswer());
    }

    // FAQ 삭제
    public void deleteFAQ(Long id) {
        FAQ faq = faqRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_END_POINT));
        faqRepository.delete(faq);
    }

    // FAQ 목록 조회
    public List<FAQResponse> getAllFAQs() {
        List<FAQ> faqs = faqRepository.findAll();
        return faqs.stream()
                .map(faq -> FAQResponse.from(faq.getId(), faq.getQuestion(), faq.getAnswer()))
                .collect(Collectors.toList());
    }
}
