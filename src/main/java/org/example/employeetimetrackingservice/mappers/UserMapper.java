package org.example.employeetimetrackingservice.mappers;

import org.example.employeetimetrackingservice.dto.DepartmentDTO;
import org.example.employeetimetrackingservice.dto.RoleDTO;
import org.example.employeetimetrackingservice.dto.UserSavedDTO;
import org.example.employeetimetrackingservice.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserSavedDTO getSavedUserDTO(User user) {
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(user.getDepartment().getId())
                .name(user.getDepartment().getName())
                .build();
        RoleDTO roleDTO = RoleDTO.builder()
                .id(user.getRole().getId())
                .title(user.getRole().getTitle())
                .build();
        UserSavedDTO  userSavedDTO = UserSavedDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .department(departmentDTO)
                .role(roleDTO)
                .profile_picture(user.getProfile_picture())
                .build();
        return userSavedDTO;
    }
}
