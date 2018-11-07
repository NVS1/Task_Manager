package ua.kiev.prog.service;

import org.springframework.stereotype.Service;
import ua.kiev.prog.model.TaskList;
import ua.kiev.prog.model.User;

import java.util.Set;

@Service
public interface TaskListService {
    TaskList getTaskListById(Long id);
    TaskList update (TaskList taskList);
    void deleteTaskList (Long id);
    TaskList createTaskList (TaskList taskList, User currentUser);
    void deleteMemberFromTaskList (User member, TaskList taskList);
}
