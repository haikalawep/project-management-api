package com.HaikalArif.project_management_api.Mapper;

import com.HaikalArif.project_management_api.Dto.ProjectDto;
import com.HaikalArif.project_management_api.Model.Project;
import com.HaikalArif.project_management_api.Model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {
    // Convert the Model to DTO
    public static ProjectDto toProjectDto (Project project) {
        if (project == null) {
            return null;
        }
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
//        projectDto.setUser(project.getUser());
        projectDto.setUserId(project.getUser().getId());
        return projectDto;
    }
    // Convert the DTO to Model
    public static Project toProjectModel (ProjectDto projectDto) {
        if (projectDto == null) {
            return null;
        }
        Project newProject = new Project();
        newProject.setName(projectDto.getName());
        newProject.setDescription(projectDto.getDescription());
//        newProject.setUser(projectDto.getUser());
        User user = new User();
        user.setId(projectDto.getUserId());
        newProject.setUser(user);

        return newProject;
    }

    // Convert List of Project to Project Dto
    public static List<ProjectDto> toProjectListDto(List<Project> project) {
        // .stream(): use to iterable the list of object
        // .map(): manipulate(convert) the object(project) in list to ProjectMapper toProjectDto
        // .collect(): get the value | Collectors.toList(): gather the toProjectDto into List
        return project.stream()
                .map(ProjectMapper::toProjectDto)
                .collect(Collectors.toList());
    }

}
