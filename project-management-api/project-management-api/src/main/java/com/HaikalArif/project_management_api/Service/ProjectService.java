package com.HaikalArif.project_management_api.Service;

import com.HaikalArif.project_management_api.Model.Project;

import com.HaikalArif.project_management_api.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    // Create new user
    public Project saveOrUpdate(Project project) {
        return projectRepository.save(project);
    }

    // Get User by ID
    public Project getProjectById(long id) {
        Optional<Project> getProject = projectRepository.findById(id);
        if (getProject.isPresent()) {
            return getProject.get();
        }
        else {
            return null;
        }
    }

    // Update User by ID
    public Project update(long id, Project project) {
        Optional<Project> projectUpdate = projectRepository.findById(id);
        if (projectUpdate.isPresent()) {
            Project _project = projectUpdate.get();
            _project.setId(id); // ID should not be changed
            _project.setName(project.getName());
            _project.setDescription(project.getDescription());
            _project.setUser(project.getUser());
            return projectRepository.save(_project);
        }
        else {
            return null;
        }
    }

    // Delete user by ID
    public void delete(long id) {
        projectRepository.deleteById(id);
    }

    // Get all user
    public List<Project> getAllProject() {
        List<Project> project = new ArrayList<Project>();
        projectRepository.findAll().forEach(project1 -> project.add(project1));
        return project;
    }
}
