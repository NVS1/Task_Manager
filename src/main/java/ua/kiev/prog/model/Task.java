package ua.kiev.prog.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ua.kiev.prog.dto.TaskDTO;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date executionDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date notifyDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @OneToOne (mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private File file;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private User executor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.JOIN)
    private User creator;

    public TaskDTO toDTO (){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(id);
        taskDTO.setTitle(title);
        taskDTO.setCreateDate(createDate);
        taskDTO.setExecuteDate(executionDate);
        taskDTO.setNotifyDate(notifyDate);
        taskDTO.setDescription(description);
        taskDTO.setStatus(status);
        taskDTO.setPriority(priority);
        taskDTO.setTaskListName(taskList.getName());
        taskDTO.setExecutor(executor.toDTO());
        return taskDTO;
    }
    public static Task fromDTO (TaskDTO taskDTO){
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setCreateDate(taskDTO.getCreateDate());
        task.setExecutionDate(taskDTO.getExecuteDate());
        task.setNotifyDate(taskDTO.getNotifyDate());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setPriority(taskDTO.getPriority());
        return task;
    }
}
