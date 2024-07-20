package students.x.studentsx.mapper;

import students.x.studentsx.dto.FeedbackDto;
import students.x.studentsx.entity.Feedback;

public class FeedbackMapper {

    public static FeedbackDto mapToFeedbackDto(Feedback feedback) {
        return new FeedbackDto(
                feedback.getId(),
                feedback.getLecture(),
                feedback.getGrade(),
                feedback.getPluses(),
                feedback.getText()
        );
    }

    public static Feedback mapToFeedback(FeedbackDto feedbackDto) {
        return new Feedback(
                feedbackDto.getId(),
                feedbackDto.getLecture(),
                feedbackDto.getGrade(),
                feedbackDto.getPluses(),
                feedbackDto.getText()
        );
    }
}
