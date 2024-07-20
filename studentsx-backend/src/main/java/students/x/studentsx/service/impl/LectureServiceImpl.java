package students.x.studentsx.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import students.x.studentsx.dto.LectureDto;
import students.x.studentsx.dto.ProfessorDto;
import students.x.studentsx.entity.Lecture;
import students.x.studentsx.entity.Professor;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.exception.ResourceNotFoundException;
import students.x.studentsx.mapper.LectureMapper;
import students.x.studentsx.mapper.ProfessorMapper;
import students.x.studentsx.repository.LectureRepository;
import students.x.studentsx.repository.ProfessorRepository;
import students.x.studentsx.service.LectureService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final ProfessorRepository professorRepository;
    private LectureRepository lectureRepository;

    @Override
    public LectureDto createLecture(LectureDto lectureDto) {
        Professor professor = professorRepository.findById(lectureDto.getProfessor().getId()).orElseThrow();
        Lecture lecture = new Lecture();
        lecture.setProfessor(professor);
        lecture.setName(lectureDto.getName());
        Lecture savedLecture = lectureRepository.save(lecture);
        return LectureMapper.mapToLectureDto(savedLecture);
    }

    @Override
    public LectureDto updateLecture(Long id, LectureDto lectureDto) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new CustomException("Lecture with id '" + id + "' not found.", "404"));

        lecture.setName(lectureDto.getName());
        lecture.setProfessor(ProfessorMapper.mapToProfessor(lectureDto.getProfessor()));

        Lecture updatedLecture = lectureRepository.save(lecture);

        return LectureMapper.mapToLectureDto(updatedLecture);
    }

    @Override
    public LectureDto getLecture(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new CustomException("Lecture with id '" + id + "' not found.", "404"));
        return LectureMapper.mapToLectureDto(lecture);
    }

    @Override
    public List<LectureDto> getLectures() {
        List<Lecture> lectures = lectureRepository.findAll();
        return lectures.stream()
                .map(LectureMapper::mapToLectureDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLecture(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new CustomException("Lecture with id '" + id + "' not found.", "404"));
        lectureRepository.delete(lecture);
    }
}
