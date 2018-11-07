package ua.kiev.prog.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.model.TaskList;
import ua.kiev.prog.model.User;
import ua.kiev.prog.repository.TaskListRepository;
import ua.kiev.prog.service.TaskListService;

import java.util.Set;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }


    @Transactional
    @Override
    public TaskList getTaskListById(Long id) {
        return taskListRepository.findById(id).orElse(new TaskList());
    }

    @Transactional
    @Override
    public TaskList update (TaskList taskList){
        return taskListRepository.save(taskList);
    }

    @Transactional
    @Override
    public void deleteTaskList (Long id){
        taskListRepository.deleteById(id);
    }

    @Transactional
    @Override
    public TaskList createTaskList (TaskList taskList, User currentUser){
        taskList.setOwner(currentUser);
        return taskListRepository.save(taskList);
    }

    @Transactional
    @Override
    public void deleteMemberFromTaskList (User member, TaskList taskList){
        taskList.getMembers().remove(member);
        update(taskList);
    }
}
