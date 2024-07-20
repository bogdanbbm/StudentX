package students.x.studentsx.service;

import students.x.studentsx.dto.LectureDto;

import java.util.List;

public interface LectureService {
    LectureDto createLecture(LectureDto lectureDto);
    LectureDto updateLecture(Long id, LectureDto lectureDto);
    LectureDto getLecture(Long id);
    List<LectureDto> getLectures();
    void deleteLecture(Long id);
}
