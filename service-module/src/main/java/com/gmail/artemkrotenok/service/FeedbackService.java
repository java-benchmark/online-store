package com.gmail.artemkrotenok.service;

import com.gmail.artemkrotenok.service.model.FeedbackDTO;

import java.util.List;

public interface FeedbackService {

    FeedbackDTO add(FeedbackDTO feedbackDTO);

    Long getCountFeedback();

    List<FeedbackDTO> getItemsByPage(int page);

    void deleteById(Long id);

}
