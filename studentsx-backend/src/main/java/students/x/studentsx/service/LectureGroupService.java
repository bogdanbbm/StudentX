package students.x.studentsx.service;

import students.x.studentsx.dto.LectureGroupDto;

import java.util.List;

public interface LectureGroupService {
    LectureGroupDto createLectureGroup(LectureGroupDto lectureGroupDto);
    List<LectureGroupDto> getLectureGroups();
    LectureGroupDto getLectureGroup(Long id);
    LectureGroupDto updateLectureGroup(Long lectureGroupId, LectureGroupDto lectureGroupDto);
    void deleteLectureGroup(Long id);
}
