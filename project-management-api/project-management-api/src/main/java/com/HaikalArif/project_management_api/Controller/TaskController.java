package com.HaikalArif.project_management_api.Controller;

import com.HaikalArif.project_management_api.Dto.TaskDto;
import com.HaikalArif.project_management_api.Model.Task;
import com.HaikalArif.project_management_api.Service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    // Create new user
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/projects/{projectId}/task")
    private Task createTasks(@PathVariable(value = "projectId") long projectId, @Valid @RequestBody TaskDto taskDto) {
        return taskService.createTasks(projectId, taskDto);
    }

    // Get user by ID
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/api/projects/{projectId}/task/{id}")
    private TaskDto getTaskByIds(@PathVariable("id") long id, @PathVariable("projectId") long projectId) {
        return taskService.getTaskById(id, projectId);
    }

    // Update user
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/projects/{projectId}/task/{id}")
    private Task updateTasks(@PathVariable("id") long id, @Valid @RequestBody TaskDto taskDto, @PathVariable("projectId") long projectId) {
        return taskService.updateTasks(id, taskDto, projectId);
    }

    // Delete User
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @DeleteMapping("/api/projects/{projectId}/task/{id}")
    private String deleteById(@PathVariable("id") long id, @PathVariable(value = "projectId") long projectId) {
        return taskService.deleteById(id, projectId);
    }

    // Get all user
//    @GetMapping("/api/projects/{projectId}/tasks")
//    private ResponseEntity<List<TaskDto>> getAllTask(@PathVariable(value = "projectId") long projectId) throws NotFoundException {
//        boolean checkProjectId = taskService.checkProjectId(projectId); // Check if project exist
//        if (!checkProjectId) {
//            throw new NotFoundException("No Project found with Id: "+ projectId);
//        }
//        List<Task> allTask = taskService.getTaskByProjectId(projectId);
//        if (!allTask.isEmpty()) {
//            List<TaskDto> allTaskDto = TaskMapper.toTaskListDto(allTask);
//            return new ResponseEntity<>(allTaskDto, HttpStatus.FOUND);
//        } else {
//            throw new NotFoundException("No task found in project " + projectId);
//        }
//    }

    // Get all user
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/api/projects/{projectId}/task")
    private List<TaskDto> getAllTasks(@PathVariable(value = "projectId") long projectId) {
        return taskService.getAllTasks(projectId);
    }

}
