package students.x.studentsx.service;

import students.x.studentsx.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    FeedbackDto addFeedback(FeedbackDto feedbackDto);
    List<FeedbackDto> getAllFeedback();
    FeedbackDto getFeedbackById(Long id);
    FeedbackDto deleteFeedbackById(Long id);
}
