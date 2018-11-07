package ua.kiev.prog.service;

import org.springframework.stereotype.Service;
import ua.kiev.prog.model.Task;
import ua.kiev.prog.model.TaskList;
import ua.kiev.prog.model.User;

import java.util.List;

@Service
public interface TaskService {
    List<Task> getTaskFromTaskList (TaskList taskList);
    Task getTaskById (Long id);
    void deleteTaskById (Long id);
    void updateTask (Task task);
    Task createTask (Task task, TaskList taskList);
}
