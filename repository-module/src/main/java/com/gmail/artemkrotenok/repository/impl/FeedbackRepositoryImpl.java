package com.gmail.artemkrotenok.repository.impl;

import com.gmail.artemkrotenok.repository.FeedbackRepository;
import com.gmail.artemkrotenok.repository.model.Feedback;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackRepositoryImpl extends GenericRepositoryImpl<Long, Feedback> implements FeedbackRepository {
}
