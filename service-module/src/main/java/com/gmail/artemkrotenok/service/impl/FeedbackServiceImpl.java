package com.gmail.artemkrotenok.service.impl;

import com.gmail.artemkrotenok.repository.FeedbackRepository;
import com.gmail.artemkrotenok.repository.model.Feedback;
import com.gmail.artemkrotenok.service.FeedbackService;
import com.gmail.artemkrotenok.service.model.FeedbackDTO;
import com.gmail.artemkrotenok.service.util.FeedbackConverterUtil;
import com.gmail.artemkrotenok.service.util.PaginationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    public static final boolean DEFUEL_VISIBLE_VALUE = true;
    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(
            FeedbackRepository feedbackRepository
    ) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    @Transactional
    public FeedbackDTO add(FeedbackDTO feedbackDTO) {
        Feedback feedback = getObjectFromDTO(feedbackDTO);
        feedback.setDate(LocalDate.now());
        feedback.setVisible(DEFUEL_VISIBLE_VALUE);
        feedbackRepository.persist(feedback);
        return getDTOFromObject(feedback);
    }

    @Override
    @Transactional
    public Long getCountFeedback() {
        return feedbackRepository.getCount();
    }

    @Override
    @Transactional
    public List<FeedbackDTO> getItemsByPage(int page) {
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<Feedback> feedbacks = feedbackRepository.getItemsByPage(startPosition, PaginationUtil.ITEMS_BY_PAGE);
        return convertItemsToItemsDTO(feedbacks);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Feedback feedback = feedbackRepository.findById(id);
        feedbackRepository.remove(feedback);
    }

    private List<FeedbackDTO> convertItemsToItemsDTO(List<Feedback> items) {
        return items.stream()
                .map(this::getDTOFromObject)
                .collect(Collectors.toList());
    }

    private FeedbackDTO getDTOFromObject(Feedback feedback) {
        return FeedbackConverterUtil.getDTOFromObject(feedback);
    }

    private Feedback getObjectFromDTO(FeedbackDTO feedbackDTO) {
        return FeedbackConverterUtil.getObjectFromDTO(feedbackDTO);
    }

}
