package ua.kiev.prog.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.model.Task;
import ua.kiev.prog.model.TaskList;
import ua.kiev.prog.model.User;
import ua.kiev.prog.repository.TaskRepository;
import ua.kiev.prog.service.TaskService;
import ua.kiev.prog.service.UserService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public List<Task> getTaskFromTaskList (TaskList taskList){
        return taskList.getTasks();
    }

    @Transactional
    @Override
    public Task getTaskById (Long id){
        return taskRepository.getOne(id);
    }

    @Transactional
    @Override
    public void deleteTaskById (Long id){
        taskRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateTask (Task task){
        taskRepository.save(task);
    }

    @Transactional
    @Override
    public Task createTask (Task task, TaskList taskList){
        if (task.getExecutor()!=null){
            String id = task.getExecutor().getId();
            User executor = userService.getUserById(id);
            executor.getAssignedTasks().add(task);
            task.setExecutor(executor);
        }
        taskList.addTask(task);
        task.setTaskList(taskList);
        return taskRepository.save(task);
    }
}
