package ua.kiev.prog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.prog.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

}
