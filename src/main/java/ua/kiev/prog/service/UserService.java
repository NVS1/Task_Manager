package ua.kiev.prog.service;

import org.springframework.stereotype.Service;
import ua.kiev.prog.model.User;

@Service
public interface UserService {
   User getUserById (String id);
   User updateUser (User user);
   User findByEmail (String email);
}
