package com.HaikalArif.project_management_api.Controller;

import com.HaikalArif.project_management_api.Dto.TaskDto;
import com.HaikalArif.project_management_api.Exception.UserNotFoundException;
import com.HaikalArif.project_management_api.Mapper.TaskMapper;
import com.HaikalArif.project_management_api.Model.Task;
import com.HaikalArif.project_management_api.Repository.ProjectRepository;
import com.HaikalArif.project_management_api.Repository.UserRepository;
import com.HaikalArif.project_management_api.Service.ProjectService;
import com.HaikalArif.project_management_api.Service.TaskService;
import com.HaikalArif.project_management_api.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    // Create new user
    @PostMapping("/projects/{projectId}/tasks")
    private ResponseEntity<Task> createTask(@PathVariable(value = "projectId") long projectId,
                                            @Valid @RequestBody TaskDto taskDto) throws UserNotFoundException {
        if (!projectRepository.existsById(projectId)) {
            throw new UserNotFoundException("Project not found with Id : "+ projectId);
        }
        Task tempTask = TaskMapper.toTaskModel(taskDto);
        Task createdTask = taskService.saveOrUpdate(tempTask);
        if (createdTask == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        }
    }

    // Get user by ID
    @GetMapping("/projects/{projectId}/tasks/{id}")
    private ResponseEntity<TaskDto> getTaskById(@PathVariable("id") long id,
                                                @PathVariable("projectId") long projectId) throws UserNotFoundException {
        if (!projectRepository.existsById(projectId)) {
            throw new UserNotFoundException("Project not found with Id : "+ projectId);
        } // Find if the project ID exist or not
        List<Task> allTask = taskService.getTaskByProjectId(projectId);
        // Return the Task with ID that exist in All task with Project ID
        if (!allTask.isEmpty()) {
//            Task getTask = taskService.getTaskById(id);
            Task getTask = taskService.getTaskByProjectIdAndID(projectId, id); // Find the Task using Project ID and Task ID
            if (getTask != null) {
                TaskDto getTaskID = TaskMapper.toTaskDto(getTask);
                return new ResponseEntity<>(getTaskID, HttpStatus.FOUND);
            } else {
                throw new UserNotFoundException("Not found Project with Id : "+ projectId + " and Task with ID : " + id);
            }
        }
//        Task getTask = taskService.getTaskById(id);
        else {
            throw new UserNotFoundException("Not found Task with ID : " + id);
        }
    }

    // Update user
    @PutMapping("/projects/{projectId}/tasks/{id}")
    private ResponseEntity<Task> updateTask(@PathVariable("id") long id, @Valid @RequestBody TaskDto taskDto,
                                            @PathVariable("projectId") long projectId) throws UserNotFoundException {
        if (!projectRepository.existsById(projectId)) {
            throw new UserNotFoundException("Project not found with Id : "+ projectId);
        } // Find if the project id exist or not
        List<Task> allTask = taskService.getTaskByProjectId(projectId);
        // Update the Task with ID that exist in All task with Project ID
        if (!allTask.isEmpty()) {
            Task tempTask = TaskMapper.toTaskModel(taskDto);
            Task updatedTask = taskService.update(id, tempTask);
            if (updatedTask != null) {
                return new ResponseEntity<>(updatedTask, HttpStatus.OK);
            } else {
                throw new UserNotFoundException("Not found Project with Id : "+ projectId + " and Task with ID : " + id);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete User
    @DeleteMapping("/projects/{projectId}/tasks/{id}")
    private ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id,
                                                  @PathVariable(value = "projectId") long projectId) throws UserNotFoundException {
        if (!projectRepository.existsById(projectId)) {
            throw new UserNotFoundException("Project not found with Id : "+ projectId);
        } // Find if the project ID exist or not

        Task getTask = taskService.getTaskByProjectIdAndID(projectId, id);
        if (getTask == null) {
            throw new UserNotFoundException("Not found Project with Id : "+ projectId + " and Task with ID : " + id);
        } else {
            taskService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Get all user
    @GetMapping("/projects/{projectId}/tasks")
    private ResponseEntity<List<TaskDto>> getAllTask(@PathVariable(value = "projectId") long projectId) throws UserNotFoundException {
        if (!projectRepository.existsById(projectId)) {
            throw new UserNotFoundException("Project not found with Id : "+ projectId);
        } else {
            List<Task> allTask = taskService.getTaskByProjectId(projectId);
            if (!allTask.isEmpty()) {
                List<TaskDto> allTaskDto = TaskMapper.toTaskListDto(allTask);
                return new ResponseEntity<>(allTaskDto, HttpStatus.FOUND);
            } else {
                throw new UserNotFoundException("No task found in project!");
            }
        }
    }
}
