package br.com.home.api.service;

import br.com.home.api.domain.RequestStage;
import br.com.home.api.domain.enums.RequestState;
import br.com.home.api.repository.RequestRepository;
import br.com.home.api.repository.RequestStageRepository;
import lombok.val;
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

        val createdStage = requestStageRepository.save(stage);

        Long requestId = stage.getRequest().getId();
        RequestState state = stage.getState();

        requestRepository.updateStatus(requestId, state);

        return createdStage;
    }

    public RequestStage getById(Long id) {
        return requestStageRepository.findById(id).get();
    }

    public Collection<RequestStage> listAllByRequestId(long requestId) {
        return requestStageRepository.findAllByRequestId(requestId);
    }

}
