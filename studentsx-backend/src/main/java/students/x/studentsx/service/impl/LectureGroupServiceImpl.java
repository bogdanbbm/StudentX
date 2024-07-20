package students.x.studentsx.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import students.x.studentsx.dto.LectureGroupDto;
import students.x.studentsx.entity.LectureGroup;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.exception.ResourceNotFoundException;
import students.x.studentsx.mapper.GroupMapper;
import students.x.studentsx.mapper.LectureGroupMapper;
import students.x.studentsx.mapper.LectureMapper;
import students.x.studentsx.repository.LectureGroupRepository;
import students.x.studentsx.service.LectureGroupService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LectureGroupServiceImpl implements LectureGroupService {

    private LectureGroupRepository lectureGroupRepository;

    @Override
    public LectureGroupDto createLectureGroup(LectureGroupDto lectureGroupDto) {
        LectureGroup lectureGroup = LectureGroupMapper.mapToLectureGroup(lectureGroupDto);
        LectureGroup savedLectureGroup = lectureGroupRepository.save(lectureGroup);
        return LectureGroupMapper.mapToLectureGroupDto(savedLectureGroup);
    }

    @Override
    public List<LectureGroupDto> getLectureGroups() {
        List<LectureGroup> students = lectureGroupRepository.findAll();
        return students.stream()
                .map(LectureGroupMapper::mapToLectureGroupDto)
                .collect(Collectors.toList());
    }

    @Override
    public LectureGroupDto getLectureGroup(Long id) {
        LectureGroup lecture = lectureGroupRepository.findById(id)
                .orElseThrow(() -> new CustomException("LectureGroup with id '" + id + "' not found.", "404"));
        return LectureGroupMapper.mapToLectureGroupDto(lecture);
    }

    @Override
    public LectureGroupDto updateLectureGroup(Long id, LectureGroupDto lectureGroupDto) {
        LectureGroup lecture = lectureGroupRepository.findById(id)
                .orElseThrow(() -> new CustomException("LectureGroup with id '" + id + "' not found.", "404"));

        lecture.setLecture(LectureMapper.mapToLecture(lectureGroupDto.getLecture()));
        lecture.setGroup(GroupMapper.mapToGroup(lectureGroupDto.getGroup()));

        LectureGroup updatedLecture = lectureGroupRepository.save(lecture);

        return LectureGroupMapper.mapToLectureGroupDto(updatedLecture);
    }

    @Override
    public void deleteLectureGroup(Long id) {
        LectureGroup lecture = lectureGroupRepository.findById(id)
                .orElseThrow(() -> new CustomException("LectureGroup with id '" + id + "' not found.", "404"));
        lectureGroupRepository.delete(lecture);
    }
}