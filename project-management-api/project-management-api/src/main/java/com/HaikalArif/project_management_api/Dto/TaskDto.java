package com.HaikalArif.project_management_api.Dto;

import com.HaikalArif.project_management_api.Enum.Status;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDto {
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "task")
    private long id;

    @NotNull(message = "Title cannot be null!")
    @NotEmpty(message = "Title cannot be empty!")
    private String title;

    private String description;
    private Status status;
//    private Project project;
//    private User user;
    private long projectId;
    private long userId;
}
