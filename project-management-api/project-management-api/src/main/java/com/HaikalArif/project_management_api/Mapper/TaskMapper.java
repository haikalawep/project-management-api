package com.HaikalArif.project_management_api.Mapper;

import com.HaikalArif.project_management_api.Dto.ProjectDto;
import com.HaikalArif.project_management_api.Dto.TaskDto;
import com.HaikalArif.project_management_api.Model.Project;
import com.HaikalArif.project_management_api.Model.Task;
import com.HaikalArif.project_management_api.Model.User;
import com.HaikalArif.project_management_api.Service.ProjectService;
import com.HaikalArif.project_management_api.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {
    @Autowired
    static ProjectService projectService;
    @Autowired
    UserService userService;

    // Convert the Model to DTO
    public static TaskDto toTaskDto (Task task) {
        if (task == null) {
            return null;
        }
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
//        taskDto.setProject(task.getProject());
//        taskDto.setUser(task.getUser());
        taskDto.setProjectId(task.getProject().getId());
        taskDto.setUserId(task.getUser().getId());
        return taskDto;
    }
    // Convert the DTO to Model
    public static Task toTaskModel (TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }
        Task newTask = new Task();
        newTask.setTitle(taskDto.getTitle());
        newTask.setDescription(taskDto.getDescription());
        newTask.setStatus(taskDto.getStatus());
//        newTask.setProject(taskDto.getProject());
//        newTask.setUser(taskDto.getUser());
        Project project = new Project();
        project.setId(taskDto.getProjectId());
        newTask.setProject(project);

        User user = new User();
        user.setId(taskDto.getUserId());
        newTask.setUser(user);
        return newTask;
    }

    // Convert List of Project to Project Dto
    public static List<TaskDto> toTaskListDto(List<Task> task) {
        // .stream(): use to iterable the list of object
        // .map(): manipulate(convert) the object(project) in list to ProjectMapper toProjectDto
        // .collect(): get the value | Collectors.toList(): gather the toProjectDto into List
        return task.stream()
                .map(TaskMapper::toTaskDto)
                .collect(Collectors.toList());
    }

}
