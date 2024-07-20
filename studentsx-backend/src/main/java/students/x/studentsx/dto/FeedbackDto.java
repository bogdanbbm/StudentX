package students.x.studentsx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    private Long id;
    private String lecture;
    private int grade;
    private List<String> pluses;
    private String text;
}
