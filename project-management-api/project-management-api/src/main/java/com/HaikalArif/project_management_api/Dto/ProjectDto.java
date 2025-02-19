package com.HaikalArif.project_management_api.Dto;

import com.HaikalArif.project_management_api.Model.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectDto {
    @NotNull(message = "Name cannot be null!")
    @NotEmpty(message = "Name cannot be empty!")
    private String name;

    private String description;

//    private User user;
    private long userId;
}
