package br.com.home.api.resource;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.RequestStage;
import br.com.home.api.service.RequestService;
import br.com.home.api.service.RequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "requests")
public class RequestResource {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestStageService requestStageService;

    @PostMapping
    public ResponseEntity<Request> save(@RequestBody Request request) {
        var createdRequest = requestService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> update(@RequestBody Request request, @PathVariable Long id) {
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

    @GetMapping("/{id}/request-stages")
    public ResponseEntity<Collection<RequestStage>> listAllRequestsById(@PathVariable Long id){
        var stages = requestStageService.listAllByRequestId(id);
        return ResponseEntity.ok(stages);
    }
}
