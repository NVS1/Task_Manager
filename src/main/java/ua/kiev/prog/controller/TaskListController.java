package ua.kiev.prog.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.kiev.prog.dto.RequestDTO;
import ua.kiev.prog.dto.TaskListDTO;
import ua.kiev.prog.dto.UserDTO;
import ua.kiev.prog.model.Request;
import ua.kiev.prog.model.TaskList;
import ua.kiev.prog.model.User;
import ua.kiev.prog.service.RequestService;
import ua.kiev.prog.service.TaskListService;
import ua.kiev.prog.service.UserService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("lists")
public class  TaskListController {
    private final TaskListService taskListService;
    private final RequestService requestService;
    private final UserService userService;

    public TaskListController(TaskListService taskListService,
                              RequestService requestService,
                              UserService userService) {
        this.taskListService = taskListService;
        this.requestService = requestService;
        this.userService = userService;
    }

    @GetMapping
    public Set<TaskListDTO> getTaskLists (@AuthenticationPrincipal User user){
        return user.getTaskLists().stream().map(TaskList::toDTO).collect(Collectors.toSet());
    }

    @GetMapping("requests")
    public Set<RequestDTO> getRequests (@AuthenticationPrincipal User user){
        return user.getRequests().stream().map(Request::toDTO).collect(Collectors.toSet());
    }

    @PutMapping("/requests/{id}/accept")
    public void acceptRequest (
            @PathVariable("id") Request request) {
        requestService.acceptRequest(request);
    }

    @PutMapping("/requests/{id}/reject")
    public void rejectRequest (@PathVariable("id") Request request){
        requestService.deleteRequest(request);
    }

    @PostMapping("/new")
    public TaskList createTaskList (
            @RequestBody TaskListDTO taskListDTO,
            @AuthenticationPrincipal User user){
        TaskList taskList = TaskList.fromDTO(taskListDTO);
        return taskListService.createTaskList(taskList, user);
    }

    @PutMapping("/{id}/edit")
    public TaskList updateTaskList (
            @PathVariable("id") TaskList taskListDb,
            @RequestBody TaskListDTO taskListDTO){
        TaskList taskList = TaskList.fromDTO(taskListDTO);
        BeanUtils.copyProperties(taskList, taskListDb, "id");
        return taskListService.update(taskListDb);
    }

    @GetMapping("/{id}/edit/members")
    public Set<UserDTO> members(
            @PathVariable("id") TaskList taskListDb,
            @AuthenticationPrincipal User user){
        if (user.getTaskLists().contains(taskListDb)) {
            return taskListDb.getMembers().stream().map(User::toDTO).collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

    @PutMapping("/{id}/edit/members/add")
    public HttpStatus addMember (
            @PathVariable("id") TaskList taskListDb,
            @RequestParam String email,
            @AuthenticationPrincipal User user){
        Request request = new Request();
        User to = userService.findByEmail(email);
        if (to != null){
            request.setFrom(user);
            request.setTo(to);
            request.setTaskList(taskListDb);
            requestService.sendRequest(request);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    @DeleteMapping("{id}")
    public void deleteTaskList (@PathVariable("id") Long id){
        taskListService.deleteTaskList(id);
    }

    @DeleteMapping("/{taskList}/edit/members/{member}")
    public void deleteMember (
            @PathVariable("taskList") TaskList taskList,
            @PathVariable("member") User member){
        taskListService.deleteMemberFromTaskList(member, taskList);
    }
}
