package ua.kiev.prog.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String name;
    private String userPic;
    private String email;
    private String locale;
    private boolean isNotify;
}
