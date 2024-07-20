package students.x.studentsx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeBookDto {
    private Long id;
    private LectureDto lecture;
    private StudentGradeDto studentGrade;
}