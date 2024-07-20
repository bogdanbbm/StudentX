package students.x.studentsx.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import students.x.studentsx.dto.FeedbackDto;
import students.x.studentsx.entity.Feedback;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.mapper.FeedbackMapper;
import students.x.studentsx.repository.FeedbackRepository;
import students.x.studentsx.service.FeedbackService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public FeedbackDto addFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = FeedbackMapper.mapToFeedback(feedbackDto);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return FeedbackMapper.mapToFeedbackDto(savedFeedback);
    }

    @Override
    public List<FeedbackDto> getAllFeedback() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        return feedbackList.stream()
                .map(FeedbackMapper::mapToFeedbackDto)
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackDto getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new CustomException("Feedback with id '" + id + "' not found.", "404"));
        return FeedbackMapper.mapToFeedbackDto(feedback);
    }

    @Override
    public FeedbackDto deleteFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new CustomException("Feedback not found with given id: " + id, "404"));
        feedbackRepository.delete(feedback);
        return FeedbackMapper.mapToFeedbackDto(feedback);
    }
}
