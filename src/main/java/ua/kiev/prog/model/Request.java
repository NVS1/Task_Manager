package ua.kiev.prog.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ua.kiev.prog.dto.RequestDTO;

import javax.persistence.*;

@Entity
@Data
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "user_from_id")
    private User from;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "user_to_id")
    private User to;

    public void send (){
        to.addRequest(this);
    }

    public void accept(){
        to.addTaskList(taskList);
        to.getRequests().remove(this);
    }

    public RequestDTO toDTO () {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(id);
        requestDTO.setFrom(from.toDTO());
        requestDTO.setTaskListName(taskList.getName());
        return requestDTO;
    }
}
