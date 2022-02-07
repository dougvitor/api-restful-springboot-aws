package br.com.home.api.service;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.enums.RequestState;
import br.com.home.api.exception.NotFoundException;
import br.com.home.api.model.PageModel;
import br.com.home.api.model.PageRequestModel;
import br.com.home.api.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request save(Request request) {
        request.setState(RequestState.OPEN);
        request.setCreationDate(new Date());
        return requestRepository.save(request);
    }

    public Request update(Request request) {
        return requestRepository.save(request);
    }

    public Request getById(Long id) {
        return requestRepository.findById(id).orElseThrow(() -> new NotFoundException("There are not request with id = " + id));
    }

    public Collection<Request> listAll() {
        return requestRepository.findAll();
    }

    public PageModel<Request> listAllOnLazyModel(PageRequestModel pageRequestModel) {
        Pageable pageable = pageRequestModel.toSpringPageRequest();
        final Page<Request> page = requestRepository.findAll(pageable);
        return new PageModel<>(
                (int) page.getTotalElements(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent()
        );
    }

    public Collection<Request> listAllByOwnerId(Long ownerId) {
        return requestRepository.findAllByOwnerId(ownerId);
    }

    public PageModel<Request> listAllByOwnerIdOnLazyModel(Long ownerId, PageRequestModel pageRequestModel){
        Pageable pageable = pageRequestModel.toSpringPageRequest();
        Page<Request> page = requestRepository.findAllByOwnerId(ownerId, pageable);
        return new PageModel<>(
                (int) page.getTotalElements(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent()
        );
    }
}
