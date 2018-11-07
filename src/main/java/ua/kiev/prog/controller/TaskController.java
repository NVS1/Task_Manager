package ua.kiev.prog.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.kiev.prog.dto.TaskDTO;
import ua.kiev.prog.model.Task;
import ua.kiev.prog.model.TaskList;
import ua.kiev.prog.model.User;
import ua.kiev.prog.service.Notifier;
import ua.kiev.prog.service.TaskListService;
import ua.kiev.prog.service.TaskService;
import ua.kiev.prog.service.UserService;
import ua.kiev.prog.service.impl.MailNotifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController {
    private final TaskListService taskListService;
    private final TaskService taskService;
    private final Notifier mailNotifier;

    public TaskController(TaskListService taskListService, TaskService taskService, MailNotifier mailNotifier) {
        this.taskListService = taskListService;
        this.taskService = taskService;
        this.mailNotifier = mailNotifier;
    }

    @GetMapping("tasks/{id}")
    public List<TaskDTO> getTasksFromTaskList(
            @PathVariable("id") TaskList taskList,
            @AuthenticationPrincipal User user) {
        if (user.getTaskLists().contains(taskList)) {
            return taskList.getTasks().stream().map(Task::toDTO).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("assigned")
    public List<TaskDTO> getAssignedTasks (@AuthenticationPrincipal User user){
        return user.getAssignedTasks().stream().map(Task::toDTO).collect(Collectors.toList());
    }

    @GetMapping("task/{id}")
    public TaskDTO getTaskById (
            @PathVariable("id") Task task,
            @AuthenticationPrincipal User user){
        if (task.getCreator().equals(user)){
            return task.toDTO();
        }
        return new TaskDTO();
    }

    @PutMapping("task/{id}/edit")
    public TaskDTO editTask (@PathVariable("id") Task taskDb,
                             @RequestBody Task task,
                             @AuthenticationPrincipal User user){
        BeanUtils.copyProperties(task, taskDb, "id");
        taskService.updateTask(taskDb);
        if (user.isNotify()){
            mailNotifier.notify(user.getEmail(), taskDb, user.getName());
        }
        return taskDb.toDTO();
    }

    @PostMapping("list/{id}task/add")
    public TaskDTO addTask (@PathVariable("id") TaskList taskList,
                            @RequestBody Task task,
                            @AuthenticationPrincipal User user){
        task.setCreator(user);
        taskService.createTask(task, taskList);
        if (user.isNotify()){
            mailNotifier.notify(user.getEmail(), task, user.getName());
        }
        return task.toDTO();
    }
}
