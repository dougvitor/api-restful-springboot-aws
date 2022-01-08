package br.com.home.api.service;

import br.com.home.api.domain.RequestStage;
import br.com.home.api.domain.enums.RequestState;
import br.com.home.api.exception.NotFoundException;
import br.com.home.api.repository.RequestRepository;
import br.com.home.api.repository.RequestStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class RequestStageService {

    @Autowired
    private RequestStageRepository requestStageRepository;

    @Autowired
    private RequestRepository requestRepository;

    public RequestStage save(RequestStage stage) {
        stage.setRealizationDate(new Date());

        var createdStage = requestStageRepository.save(stage);

        Long requestId = stage.getRequest().getId();
        RequestState state = stage.getState();

        requestRepository.updateStatus(requestId, state);

        return createdStage;
    }

    public RequestStage getById(Long id) {
        return requestStageRepository.findById(id).orElseThrow(() -> new NotFoundException("There are not request stage with id = " + id));
    }

    public Collection<RequestStage> listAllByRequestId(long requestId) {
        return requestStageRepository.findAllByRequestId(requestId);
    }

}
