package com.HaikalArif.project_management_api.Controller;

import com.HaikalArif.project_management_api.Dto.ProjectDto;
import com.HaikalArif.project_management_api.Exception.UserNotFoundException;
import com.HaikalArif.project_management_api.Mapper.ProjectMapper;
import com.HaikalArif.project_management_api.Model.Project;
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
    @PostMapping("/projects")
    private ResponseEntity<Project> createProject(@Valid @RequestBody ProjectDto projectDto) {
        try {
            Project tempProject = ProjectMapper.toProjectModel(projectDto); // Convert the Project DTO to Project model
            Project createProject = projectService.saveOrUpdate(tempProject);
            if (createProject == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(createProject, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get user by ID
    @GetMapping("/projects/{id}")
    private ResponseEntity<ProjectDto> getProjectById(@PathVariable("id") long id) throws UserNotFoundException {
        Project getProject = projectService.getProjectById(id);
        if (getProject != null) {
            ProjectDto getProjectID = ProjectMapper.toProjectDto(getProject);
            return new ResponseEntity<>(getProjectID, HttpStatus.FOUND);
        } else {
            throw new UserNotFoundException("Project not found with Id : " + id);
        }
    }

    // Update user
    @PutMapping("/projects/{id}")
    private ResponseEntity<Project> updateProject(@PathVariable("id") long id, @Valid @RequestBody ProjectDto projectDto) throws UserNotFoundException {
        Project tempProject = ProjectMapper.toProjectModel(projectDto);
        Project updatedProject = projectService.update(id, tempProject);
        if (updatedProject != null) {
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("Unsuccessful update! Project not found with Id : " + id);
        }
    }

    // Delete User
    @DeleteMapping("/projects/{id}")
    private ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) throws UserNotFoundException {
        Project getProject = projectService.getProjectById(id);
        if (getProject == null) {
            throw new UserNotFoundException("Project not found with Id : " + id);
        } else {
            projectService.delete(id);
            return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
        }
    }

    // Get all user
    @GetMapping("/projects")
    private ResponseEntity<List<ProjectDto>> getAllProject() {
        try {
            List<Project> allProject = projectService.getAllProject();
            List<ProjectDto> allProjectDto = ProjectMapper.toProjectListDto(allProject);
            return new ResponseEntity<>(allProjectDto, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
