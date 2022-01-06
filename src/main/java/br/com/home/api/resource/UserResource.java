package br.com.home.api.resource;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.User;
import br.com.home.api.dto.UserLoginDto;
import br.com.home.api.service.RequestService;
import br.com.home.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        var createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Long id){
        user.setId(id);
        User updateUser = userService.update(user);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        var user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> listAll(){
        var users = userService.listAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserLoginDto userLoginDto){
        var loggedUser = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
        return ResponseEntity.ok(loggedUser);
    }

    @GetMapping("/{id}/requests")
    public ResponseEntity<Collection<Request>> listAllRequestsById(@PathVariable Long id){
        var requests = requestService.listAllByOwnerId(id);
        return ResponseEntity.ok(requests);
    }
}
