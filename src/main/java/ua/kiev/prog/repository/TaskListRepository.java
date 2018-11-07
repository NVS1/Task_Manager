package ua.kiev.prog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kiev.prog.model.TaskList;

import java.util.Optional;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
}
