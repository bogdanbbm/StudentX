package students.x.studentsx.mapper;

import students.x.studentsx.dto.LectureDto;
import students.x.studentsx.entity.Lecture;

public class LectureMapper {
    
        public static LectureDto mapToLectureDto(Lecture lecture) {
            return new LectureDto(
                    lecture.getId(),
                    lecture.getName(),
                    ProfessorMapper.mapToProfessorDto(lecture.getProfessor())
            );
        }
    
        public static Lecture mapToLecture(LectureDto lectureDto) {
            return new Lecture(
                    lectureDto.getId(),
                    lectureDto.getName(),
                        ProfessorMapper.mapToProfessor(lectureDto.getProfessor())
            );
        }
}