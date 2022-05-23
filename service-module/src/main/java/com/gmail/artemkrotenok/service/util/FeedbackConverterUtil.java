package com.gmail.artemkrotenok.service.util;

import com.gmail.artemkrotenok.repository.model.Feedback;
import com.gmail.artemkrotenok.service.model.FeedbackDTO;

public class FeedbackConverterUtil {

    public static FeedbackDTO getDTOFromObject(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId(feedback.getId());
        feedbackDTO.setUserDTO(UserConverterUtil.getDTOFromObject(feedback.getUser()));
        feedbackDTO.setContent(feedback.getContent());
        feedbackDTO.setDate(feedback.getDate());
        feedbackDTO.setVisible(feedback.getVisible());
        return feedbackDTO;
    }

    public static Feedback getObjectFromDTO(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setUser(UserConverterUtil.getObjectFromDTO(feedbackDTO.getUserDTO()));
        feedback.setContent(feedbackDTO.getContent());
        feedback.setDate(feedbackDTO.getDate());
        feedback.setVisible(feedbackDTO.getVisible());
        return feedback;
    }

}
