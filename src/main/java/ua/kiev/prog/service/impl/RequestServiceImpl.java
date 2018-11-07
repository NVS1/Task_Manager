package ua.kiev.prog.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.model.Request;
import ua.kiev.prog.model.User;
import ua.kiev.prog.repository.RequestRepository;
import ua.kiev.prog.service.RequestService;

import java.util.Set;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Transactional
    @Override
    public Request getRequestById (Long id){
        return requestRepository.getOne(id);
    }

    @Transactional
    @Override
    public void sendRequest (Request request){
        request.send();
        requestRepository.save(request);
    }

    @Transactional
    @Override
    public void acceptRequest (Request request){
        request.accept();
        deleteRequest(request);
    }

    @Transactional
    @Override
    public void deleteRequest (Request request){
        requestRepository.delete(request);
    }
}
