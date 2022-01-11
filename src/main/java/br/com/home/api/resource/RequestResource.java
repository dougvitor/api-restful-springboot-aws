package br.com.home.api.resource;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.RequestStage;
import br.com.home.api.domain.User;
import br.com.home.api.dto.RequestSaveDto;
import br.com.home.api.dto.RequestUpdateDto;
import br.com.home.api.model.PageModel;
import br.com.home.api.model.PageRequestModel;
import br.com.home.api.service.RequestService;
import br.com.home.api.service.RequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(value = "requests")
public class RequestResource {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestStageService requestStageService;

    @PostMapping
    public ResponseEntity<Request> save(@RequestBody @Valid RequestSaveDto requestSaveDto) {
        var createdRequest = requestService.save(requestSaveDto.convertToRequest());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> update(@RequestBody @Valid RequestUpdateDto requestUpdateDto, @PathVariable Long id) {
        Request request = requestUpdateDto.convertToRequest();
        request.setId(id);
        Request updateUser = requestService.update(request);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getById(@PathVariable Long id) {
        var request = requestService.getById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping
    public ResponseEntity<Collection<Request>> listAll() {
        var requests = requestService.listAll();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/list-all-pageable")
    public ResponseEntity<PageModel<Request>> listAllPageable(PageRequestModel pageRequestModel){
        final PageModel<Request> pageModel = requestService.listAllOnLazyModel(pageRequestModel);
        return ResponseEntity.ok(pageModel);
    }

    @GetMapping("/{id}/request-stages")
    public ResponseEntity<Collection<RequestStage>> listAllRequestsById(@PathVariable Long id){
        var stages = requestStageService.listAllByRequestId(id);
        return ResponseEntity.ok(stages);
    }

    @GetMapping("/{id}/request-stages-pageable")
    public ResponseEntity<PageModel<RequestStage>> listAllRequestsById(@PathVariable Long id, PageRequestModel pageRequestModel){
        final PageModel<RequestStage> pageModel = requestStageService.listAllByRequestIddOnLazyModel(id, pageRequestModel);
        return ResponseEntity.ok(pageModel);
    }
}
