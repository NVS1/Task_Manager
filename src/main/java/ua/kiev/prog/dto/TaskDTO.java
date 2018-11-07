package ua.kiev.prog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ua.kiev.prog.model.Priority;
import ua.kiev.prog.model.Status;

import javax.persistence.Converter;
import java.lang.annotation.Annotation;
import java.util.Date;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date executeDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date notifyDate;
    private String description;
    private Status status;
    private Priority priority;
    private String taskListName;
    private UserDTO executor;
}
