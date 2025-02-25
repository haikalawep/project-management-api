package com.HaikalArif.project_management_api.Service;

import com.HaikalArif.project_management_api.Dto.TaskDto;
import com.HaikalArif.project_management_api.Enum.Role;
import com.HaikalArif.project_management_api.Exception.NotFoundException;
import com.HaikalArif.project_management_api.Exception.WrongRoleException;
import com.HaikalArif.project_management_api.Mapper.TaskMapper;
import com.HaikalArif.project_management_api.Model.Task;
import com.HaikalArif.project_management_api.Model.User;
import com.HaikalArif.project_management_api.Repository.ProjectRepository;
import com.HaikalArif.project_management_api.Repository.TaskRepository;
import com.HaikalArif.project_management_api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    // Create new Task
    public Task createTasks(long projectId, TaskDto taskDto) {
        boolean checkProjectId = checkProjectId(projectId); // Check if project exist
        if (!checkProjectId) {
            throw new NotFoundException("No Project with Id "+ projectId);
        }
        Task tempTask = TaskMapper.toTaskModel(taskDto, projectId);
        Task createdTask = save(tempTask);
        if (createdTask == null) {
            throw new WrongRoleException("Inserted userID not USER!");
        }
        return createdTask;
    }

    public Task save(Task task) {
        Optional<User> getUserId = userRepository.findById(task.getUser().getId());
        Role role = getUserId.get().getRole();
        if (role != Role.USER) {
            return null;
        }
        return taskRepository.save(task);
    }

    // Get Task by Id
    public TaskDto getTaskById(long id, long projectId) {
        boolean checkProjectId = checkProjectId(projectId); // Check if project exist
        if (!checkProjectId) {
            throw new NotFoundException("No Project with Id "+ projectId);
        }

        List<Task> allTask = getTaskByProjectId(projectId); // List all in ProjectId
        if (allTask.isEmpty()) {
            throw new NotFoundException("No Task with Id " + id + " in Project " + projectId); // Project Exist
        }

        Task getTask = getTaskByProjectIdAndID(projectId, id); // Find Task using projectID and taskID
        if (getTask == null) {
            throw new NotFoundException("No Project Id "+ projectId + " in Task " + id);
        }

        return TaskMapper.toTaskDto(getTask); // Task Exist
    }

    // Update Task
    public Task updateTasks(long id, TaskDto taskDto, long projectId) {
        boolean checkProjectId = checkProjectId(projectId); // Check if project exist
        if (!checkProjectId) {
            throw new NotFoundException("No Project with Id "+ projectId);
        }

        List<Task> allTask = getTaskByProjectId(projectId); // List all
        if (allTask.isEmpty()) { // If not empty
            throw new NotFoundException("INTERNAL SERVER ERROR");
        }

        Task tempTask = TaskMapper.toTaskModel(taskDto, projectId); // Bring input DTO and projectID to mapper
        Task updatedTask = update(id, tempTask); // Update Task with Id that exist in All task with Project ID
        if (updatedTask == null) {
            throw new NotFoundException("No Project Id "+ projectId + " in Task Id " + id);
        }
        return updatedTask;
    }

    public Task update(long id, Task task) {
        Optional<Task> taskExisted = taskRepository.findById(id); // Find task using Id
        if (taskExisted.isEmpty()) {
            throw new NotFoundException("Task not existed!");
        }
        long inputTaskProjectId = task.getProject().getId(); // projectId from url(controller)
        long taskExistedProjectId = taskExisted.get().getProject().getId(); // projectId from found task
        //  Compared if id from url same or not with in task |
        //  To ensure only projectId and taskId in url will be updated
        if (inputTaskProjectId != taskExistedProjectId) {
            throw new NotFoundException("Wrong input of Project Id in Task: "+id);
        }
        Task task_ = TaskMapper.toTask(task);
        task_.setId(id);
        return taskRepository.save(task_);
    }

    // Get Task by Project Id and Task Id
    public Task getTaskByProjectIdAndID(long projectId, long id) {
        Optional<Task> getTask = taskRepository.findByProjectIdAndId(projectId, id);
        if (getTask.isEmpty()) {
            return null;
        }
        return getTask.get();
    }

    public List<Task> getTaskByProjectId(long projectId) {
        return taskRepository.findByProjectId(projectId); // Assuming you have a method like this in the repository
    }

    public boolean checkProjectId(long projectId) {
        boolean checkId = projectRepository.existsById(projectId);
        if (!checkId) {return false;}
        return true;
    }

    // Delete Task by ID
    public String deleteById(long id, long projectId) {
        boolean checkProjectId = checkProjectId(projectId); // Check if project exist
        if (!checkProjectId) {
            throw new NotFoundException("No Project with Id "+ projectId);
        }
        Task getTask = getTaskByProjectIdAndID(projectId, id);
        if (getTask == null) {
            throw new NotFoundException("No Project Id "+ projectId + " in Task with " + id);
        }
        taskRepository.deleteById(id);
        return "Successfully delete Project "+ id;
    }

    // Get all Task
    public List<TaskDto> getAllTasks(long projectId) {
        boolean checkProjectId = checkProjectId(projectId); // Check if project exist
        if (!checkProjectId) {
            throw new NotFoundException("No Project found with Id: "+ projectId);
        }
        List<Task> allTask = getTaskByProjectId(projectId);
        if (allTask.isEmpty()) {
            throw new NotFoundException("No task found in project " + projectId);
        }
        List<TaskDto> allTaskDto = TaskMapper.toTaskListDto(allTask);
        return allTaskDto;
    }
//    public List<Task> getAllTask() {
//        List<Task> task = new ArrayList<Task>();
//        taskRepository.findAll().forEach(task1 -> task.add(task1));
//        return task;
//    }


}
