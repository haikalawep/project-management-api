package com.HaikalArif.project_management_api.Dto;

import com.HaikalArif.project_management_api.Enum.Role;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    @NotNull(message = "Name cannot be null!")
    @NotEmpty(message = "Name cannot be empty!")
    private String name;

    @NotNull(message = "Email cannot be null!")
    @NotEmpty(message = "Email cannot be empty!")
    @Email(message = "Invalid email address!")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "Password cannot be null!")
    @NotEmpty(message = "Password cannot be empty!")
    private String password;
    private Role role;

}
