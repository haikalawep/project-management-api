package com.HaikalArif.project_management_api.Service;

import com.HaikalArif.project_management_api.Dto.ProjectDto;
import com.HaikalArif.project_management_api.Dto.UserDto;
import com.HaikalArif.project_management_api.Exception.NotFoundException;
import com.HaikalArif.project_management_api.Exception.WrongRoleException;
import com.HaikalArif.project_management_api.Mapper.ProjectMapper;
import com.HaikalArif.project_management_api.Mapper.UserMapper;
import com.HaikalArif.project_management_api.Model.Project;
import com.HaikalArif.project_management_api.Enum.Role;
import com.HaikalArif.project_management_api.Model.User;
import com.HaikalArif.project_management_api.Repository.ProjectRepository;
import com.HaikalArif.project_management_api.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    // Create new project
    public Project createProjects(ProjectDto projectDto) {
        Project tempProject = ProjectMapper.toProjectModel(projectDto); // Convert the Project DTO to Project model
        Project createProject = save(tempProject);
        if (createProject == null) {
            throw new WrongRoleException("Inserted userID not ADMIN!");
        }
        return createProject;
    }

    // Save project from create user
    public Project save(Project project) {
        Optional<User> getUserIds = userRepository.findById(project.getUser().getId()); // Find userId
        Role role = getUserIds.get().getRole();
        if (role != Role.ADMIN) {return null;} // Check if project create was by ADMIN or not
        return projectRepository.save(project);
    }

    // Get Project by ID
    public ProjectDto getProjectById(long id) {
        Optional<Project> getProject = projectRepository.findById(id);
        if (getProject.isEmpty()) {
            throw new NotFoundException("Not Found Project with ID " + id);
        }
        ProjectDto convertProject = ProjectMapper.toProjectDto(getProject.get());
        return convertProject;
    }

    // Update Project by ID
    public Project updateProject(long id, ProjectDto projectDto) {
        Project tempProject = ProjectMapper.toProjectModel(projectDto);
        Project updatedProject = update(id, tempProject);
        if (updatedProject == null) {
            throw new NotFoundException("Unsuccessful Update! No Project with Id "+ id);
        }
        return updatedProject;
    }

    // Save from update project
    public Project update(long id, Project project) {
        Optional<Project> projectUpdate = projectRepository.findById(id);
        long userId = project.getUser().getId();
        Optional<User> getUserIds = userRepository.findById(userId); // Find userId
        Role role = getUserIds.get().getRole(); // Find role user

        // Only userId with Role ADMIN can be selected for updating
        if (role != Role.ADMIN) {return null;}
        if (projectUpdate.isEmpty()) { return null;}

        Project project_ = ProjectMapper.toProject(project);
        project_.setId(id);

        return projectRepository.save(project_); // Can use Mapper/Model
    }

    // Delete Project by Id
    public String deleteById(long id) {
        Optional<Project> getProject = projectRepository.findById(id);
        if (getProject.isEmpty()) {
            throw new NotFoundException("Project not found with Id " + id);
        }
        projectRepository.deleteById(id);
        return "Successfully delete Project "+ id;
    }

    // Get all project
    public List<ProjectDto> getAllProject() {
        List<Project> project = new ArrayList<Project>();
        projectRepository.findAll().forEach(project1 -> project.add(project1));
        List<ProjectDto> allProjectDto = ProjectMapper.toProjectListDto(project);
        if (project.isEmpty()) {
            throw new NotFoundException("Project not found!");
        }
        return allProjectDto;
    }
}
