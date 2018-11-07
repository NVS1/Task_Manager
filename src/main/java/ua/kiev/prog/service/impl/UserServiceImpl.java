package ua.kiev.prog.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.model.User;
import ua.kiev.prog.repository.UserRepository;
import ua.kiev.prog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User getUserById(String id) {
       return userRepository.getOne(id);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
       return userRepository.save(user);
    }

    @Transactional
    @Override
    public User findByEmail (String email){
        return userRepository.getUserByEmail(email);
    }
}
