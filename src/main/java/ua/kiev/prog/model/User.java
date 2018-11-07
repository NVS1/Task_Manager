package ua.kiev.prog.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ua.kiev.prog.dto.UserDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "usr")
public class User implements Serializable {
    @Id
    @Column(name = "id", length = 150)
    private String id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_picture")
    private String userPic;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_locale")
    private String locale;
    private boolean isNotify;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "TaskList_User",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "taskList_id", referencedColumnName = "id"))
    private Set<TaskList> taskLists = new HashSet<>();
    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<Request> requests = new HashSet<>();
    @OneToMany (mappedBy = "executor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<Task> assignedTasks = new HashSet<>();

    public void addTaskList (TaskList taskList){
        this.taskLists.add(taskList);
    }

    public void addRequest (Request request){
        requests.add(request);
    }

    public UserDTO toDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setName(name);
        userDTO.setUserPic(userPic);
        userDTO.setEmail(email);
        userDTO.setLocale(locale);
        userDTO.setNotify(isNotify);
        return userDTO;
    }

    public static User fromDTO (UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setUserPic(userDTO.getUserPic());
        user.setEmail(userDTO.getEmail());
        user.setLocale(userDTO.getLocale());
        user.setNotify(userDTO.isNotify());
        return user;
    }
}
