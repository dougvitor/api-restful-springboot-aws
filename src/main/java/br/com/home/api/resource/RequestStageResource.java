package br.com.home.api.resource;

import br.com.home.api.domain.RequestStage;
import br.com.home.api.dto.RequestStageSaveDto;
import br.com.home.api.service.RequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "request-stages")
public class RequestStageResource {

    @Autowired
    private RequestStageService requestStageService;

    @PostMapping
    public ResponseEntity<RequestStage> save(@RequestBody @Valid RequestStageSaveDto requestStageSaveDto) {
        var createdRequestStage = requestStageService.save(requestStageSaveDto.convertToRequestStage());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequestStage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestStage> getById(@PathVariable Long id) {
        var stage = requestStageService.getById(id);
        return ResponseEntity.ok(stage);
    }
}
