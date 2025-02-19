package com.HaikalArif.project_management_api.Service;

import com.HaikalArif.project_management_api.Model.Project;
import com.HaikalArif.project_management_api.Model.Task;
import com.HaikalArif.project_management_api.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    // Create new user
    public Task saveOrUpdate(Task task) {
        return taskRepository.save(task);
    }

    // Get User by ID
    public Task getTaskByProjectIdAndID(long projectId, long id) {
//        Optional<Task> getTask = taskRepository.findById(id);
        Optional<Task> getTask = taskRepository.findByProjectIdAndId(projectId, id);
        if (getTask.isPresent()) {
            return getTask.get();
        }
        else {
            return null;
        }
    }

    public List<Task> getTaskByProjectId(long projectId) {
        return taskRepository.findByProjectId(projectId); // Assuming you have a method like this in the repository
    }

    // Update User by ID
    public Task update(long id, Task task) {
        Optional<Task> taskUpdate = taskRepository.findById(id);
        if (taskUpdate.isPresent()) {
            Task _task = taskUpdate.get();
            _task.setId(id); // ID should not changed
            _task.setTitle(task.getTitle());
            _task.setDescription(task.getDescription());
            _task.setStatus(task.getStatus());
            _task.setProject(task.getProject());
            _task.setUser(task.getUser());
            return taskRepository.save(_task);
        }
        else {
            return null;
        }
    }

    // Delete user by ID
    public void delete(long id) {
        taskRepository.deleteById(id);
    }

    // Get all user
    public List<Task> getAllTask() {
        List<Task> task = new ArrayList<Task>();
        taskRepository.findAll().forEach(task1 -> task.add(task1));
        return task;
    }
}
