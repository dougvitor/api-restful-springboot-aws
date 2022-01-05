package br.com.home.api.service;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.enums.RequestState;
import br.com.home.api.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request save(Request request){
        request.setState(RequestState.OPEN);
        request.setCreationDate(new Date());
        return requestRepository.save(request);
    }

    public Request update(Request request){
        return requestRepository.save(request);
    }

    public Request getById(Long id){
        return requestRepository.findById(id).get();
    }

    public Collection<Request> listAll(){
        return requestRepository.findAll();
    }

    public Collection<Request> listAllByOwnerId(Long ownerId){
        return requestRepository.findAllByOwnerId(ownerId);
    }

}
