package students.x.studentsx.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import students.x.studentsx.dto.GroupDto;
import students.x.studentsx.entity.Group;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.exception.ResourceNotFoundException;
import students.x.studentsx.mapper.GroupMapper;
import students.x.studentsx.repository.GroupRepository;
import students.x.studentsx.service.GroupService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    @Override
    public GroupDto createGroup(GroupDto groupDto) {
        if (this.checkGroup(groupDto.getName())) {
            throw new CustomException("Group with name '" + groupDto.getName() + "' already exists.", "409");
        }

        if (groupDto.getName().length() <= 0) {
            throw new CustomException("Group name cannot be empty.", "409");
        }

        Group group = GroupMapper.mapToGroup(groupDto);
        Group savedGroup = groupRepository.save(group);
        return GroupMapper.mapToGroupDto(savedGroup);
    }

    @Override
    public GroupDto updateGroup(Long id, GroupDto groupDto) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new CustomException("Group with id '" + id + "' not found.", "404"));

        group.setName(groupDto.getName());

        Group savedGroup = groupRepository.save(group);

        return GroupMapper.mapToGroupDto(savedGroup);
    }

    @Override
    public GroupDto getGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new CustomException("Group with id '" + id + "' not found.", "404"));

        return GroupMapper.mapToGroupDto(group);
    }

    @Override
    public Boolean checkGroup(String groupName) {
        List<Group> groups = groupRepository.findAll();
        for (Group group : groups) {
            if (group.getName().equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<GroupDto> getGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(GroupMapper::mapToGroupDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new CustomException("Group with id '" + id + "' not found.", "404"));
        groupRepository.delete(group);
    }


}

