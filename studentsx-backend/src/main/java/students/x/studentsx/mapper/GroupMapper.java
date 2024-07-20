package students.x.studentsx.mapper;

import students.x.studentsx.dto.GroupDto;
import students.x.studentsx.entity.Group;

public class GroupMapper {
    public static GroupDto mapToGroupDto(Group group) {
        return new GroupDto(
                group.getId(),
                group.getName()
        );
    }

    public static Group mapToGroup(GroupDto groupDto) {
        return new Group(
                groupDto.getId(),
                groupDto.getName()
        );
    }
}
