package ua.kiev.prog.service;

import org.springframework.stereotype.Service;
import ua.kiev.prog.model.Request;
import ua.kiev.prog.model.User;

import java.util.Set;

@Service
public interface RequestService {
    Request getRequestById (Long id);
    void sendRequest (Request request);
    void acceptRequest (Request request);
    void deleteRequest (Request request);
}
