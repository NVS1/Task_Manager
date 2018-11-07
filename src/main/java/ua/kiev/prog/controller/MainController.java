package ua.kiev.prog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.kiev.prog.model.User;
import ua.kiev.prog.service.UserService;

@RestController
@RequestMapping("/")
public class MainController {
    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User index (@AuthenticationPrincipal User user){
       return user;
    }

    @PutMapping("/notify/{val}")
    public void setNotify (@AuthenticationPrincipal User user,
                           @PathVariable("val") boolean val){
        user.setNotify(val);
        userService.updateUser(user);
    }
}
