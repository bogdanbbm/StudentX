package students.x.studentsx.mapper;

import students.x.studentsx.dto.LectureGroupDto;
import students.x.studentsx.entity.LectureGroup;

public class LectureGroupMapper {
    public static LectureGroupDto mapToLectureGroupDto(LectureGroup lectureGroup) {
        return new LectureGroupDto(
                lectureGroup.getId(),
                LectureMapper.mapToLectureDto(lectureGroup.getLecture()),
                GroupMapper.mapToGroupDto(lectureGroup.getGroup())
        );
    }
    
    public static LectureGroup mapToLectureGroup(LectureGroupDto lectureGroupDto) {
        return new LectureGroup(
                lectureGroupDto.getId(),
                LectureMapper.mapToLecture(lectureGroupDto.getLecture()),
                GroupMapper.mapToGroup(lectureGroupDto.getGroup())
        );
    }
}
