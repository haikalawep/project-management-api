package com.HaikalArif.project_management_api.Controller;

import com.HaikalArif.project_management_api.Dto.ProjectDto;
import com.HaikalArif.project_management_api.Exception.NotFoundException;
import com.HaikalArif.project_management_api.Exception.WrongRoleException;
import com.HaikalArif.project_management_api.Mapper.ProjectMapper;
import com.HaikalArif.project_management_api.Model.Project;
import com.HaikalArif.project_management_api.Model.User;
import com.HaikalArif.project_management_api.Service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    // Create new user
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/project")
    private Project createProjects(@Valid @RequestBody ProjectDto projectDto) {
        return projectService.createProjects(projectDto);
    }

    // Get user by ID
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/api/project/{id}")
    private ProjectDto getProjectByIds(@PathVariable("id") long id) {
        return projectService.getProjectById(id);
    }

    // Update user
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/project/{id}")
    private Project updateProjects(@PathVariable("id") long id, @Valid @RequestBody ProjectDto projectDto) {
        return projectService.updateProject(id, projectDto);

    }

//    // Delete User
//    @DeleteMapping("/api/projects/{id}")
//    private ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) {
//        Project getProject = projectService.getProjectById(id);
//        if (getProject == null) {
//            throw new NotFoundException("Project not found with Id " + id);
//        } else {
//            projectService.delete(id);
//            return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
//        }
//    }

    // Delete User
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @DeleteMapping("/api/project/{id}")
    private String deleteByIds(@PathVariable("id") long id) {
        return projectService.deleteById(id);
    }

    // Get all user
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/api/project")
    private List<ProjectDto> getAllProject() {
        return projectService.getAllProject();
    }
}
