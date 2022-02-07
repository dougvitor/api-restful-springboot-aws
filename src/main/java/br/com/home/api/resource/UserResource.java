package br.com.home.api.resource;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.User;
import br.com.home.api.dto.*;
import br.com.home.api.model.PageModel;
import br.com.home.api.model.PageRequestModel;
import br.com.home.api.security.JwtManager;
import br.com.home.api.service.RequestService;
import br.com.home.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtManager jwtManager;

    @Secured({"ROLE_ADMINISTRATOR"})
    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserSaveDto userSaveDto) {
        var createdUser = userService.save(userSaveDto.convertToUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("@accessManager.isOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody @Valid UserUpdateDto userUpdateDto, @PathVariable Long id) {
        User user = userUpdateDto.convertToUser();
        user.setId(id);
        User updateUser = userService.update(user);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        var user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> listAll() {
        var users = userService.listAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/list-all-pageable")
    public ResponseEntity<PageModel<User>> listAllPageable(@RequestParam Map<String, String> params) {
        PageRequestModel pageRequestModel = new PageRequestModel(params);
        final PageModel<User> pageModel = userService.listAllOnLazyModel(pageRequestModel);
        return ResponseEntity.ok(pageModel);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        org.springframework.security.core.userdetails.User userSpring =
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        String email = userSpring.getUsername();
        List<String> roles = userSpring.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(jwtManager.createToken(email, roles));
    }

    @GetMapping("/{id}/requests")
    public ResponseEntity<Collection<Request>> listAllRequestsById(@PathVariable Long id) {
        var requests = requestService.listAllByOwnerId(id);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}/requests-pageable")
    public ResponseEntity<PageModel<Request>> listAllRequestsByIdPageable(@PathVariable Long id, PageRequestModel pageRequestModel) {
        final PageModel<Request> pageModel = requestService.listAllByOwnerIdOnLazyModel(id, pageRequestModel);
        return ResponseEntity.ok(pageModel);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid UserUpdateRoleDto userUpdateRoleDto) {
        User user = new User();
        user.setId(id);
        user.setRole(userUpdateRoleDto.getRole());
        userService.updateRole(user);

        return ResponseEntity.ok().build();
    }
}
