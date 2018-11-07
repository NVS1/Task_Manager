package ua.kiev.prog.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kiev.prog.dto.TaskListDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "task_list")
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();
    @ManyToMany(mappedBy = "taskLists", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> members = new HashSet<>();
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public void addTask(Task task){
        tasks.add(task);
    }

    public void addMember (User user){
        members.add(user);
        user.addTaskList(this);
    }

    public TaskListDTO toDTO (){
        TaskListDTO taskListDTO = new TaskListDTO();
        taskListDTO.setId(id);
        taskListDTO.setName(name);
        return taskListDTO;
    }

    public static TaskList fromDTO (TaskListDTO taskListDTO){
        TaskList taskList = new TaskList();
        taskList.setId(taskListDTO.getId());
        taskList.setName(taskListDTO.getName());
        return taskList;
    }
}
