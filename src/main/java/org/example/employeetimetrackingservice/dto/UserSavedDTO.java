package org.example.employeetimetrackingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSavedDTO {
    private Long id;
    private String name;
    private String password;
    private byte[] profile_picture;
    private DepartmentDTO department;
    private RoleDTO role;
}
