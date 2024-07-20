package students.x.studentsx.service;

import students.x.studentsx.dto.GroupDto;

import java.util.List;

public interface GroupService {
    GroupDto createGroup(GroupDto group);
    GroupDto updateGroup(Long id, GroupDto group);
    GroupDto getGroup(Long id);
    Boolean checkGroup(String groupName);
    List<GroupDto> getGroups();
    void deleteGroup(Long id);
}
