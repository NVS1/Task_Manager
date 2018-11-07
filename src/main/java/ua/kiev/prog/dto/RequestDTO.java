package ua.kiev.prog.dto;

import lombok.Data;

@Data
public class RequestDTO {
    private Long id;
    private String taskListName;
    private UserDTO from;
}
