package ua.kiev.prog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kiev.prog.model.Task;
import ua.kiev.prog.model.TaskList;
import ua.kiev.prog.model.User;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t JOIN FETCH t.executor WHERE t.executor = :user")
    List<Task> getTaskByExecutor (@Param("user") User user);

}
