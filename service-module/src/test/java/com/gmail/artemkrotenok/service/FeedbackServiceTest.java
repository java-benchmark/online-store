package com.gmail.artemkrotenok.service;

import com.gmail.artemkrotenok.repository.FeedbackRepository;
import com.gmail.artemkrotenok.repository.model.Feedback;
import com.gmail.artemkrotenok.repository.model.User;
import com.gmail.artemkrotenok.repository.model.UserInformation;
import com.gmail.artemkrotenok.service.impl.FeedbackServiceImpl;
import com.gmail.artemkrotenok.service.model.FeedbackDTO;
import com.gmail.artemkrotenok.service.util.FeedbackConverterUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    public static final String TEST_FEEDBACK_CONTENT = "Test content";

    @Mock
    private FeedbackRepository feedbackRepository;

    private FeedbackService feedbackService;

    @BeforeEach
    public void setup() {
        this.feedbackService = new FeedbackServiceImpl(feedbackRepository);
    }

    @Test
    public void add_returnFeedbackDTO() {
        FeedbackDTO feedbackDTO = FeedbackConverterUtil.getDTOFromObject(getEmptyFeedback());
        feedbackDTO.setContent(TEST_FEEDBACK_CONTENT);
        doNothing().when(feedbackRepository).persist(any(Feedback.class));
        FeedbackDTO addedFeedbackDTO = feedbackService.add(feedbackDTO);
        Assertions.assertThat(addedFeedbackDTO).isNotNull();
        Assertions.assertThat(addedFeedbackDTO.getContent()).isEqualTo(feedbackDTO.getContent());
        verify(feedbackRepository, times(1)).persist(any(Feedback.class));
    }

    private Feedback getEmptyFeedback() {
        User user = new User();
        user.setUserInformation(new UserInformation());
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        return feedback;
    }

}
