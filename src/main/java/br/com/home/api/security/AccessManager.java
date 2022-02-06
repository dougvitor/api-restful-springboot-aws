package br.com.home.api.security;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.User;
import br.com.home.api.exception.NotFoundException;
import br.com.home.api.repository.UserRepository;
import br.com.home.api.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component("accessManager")
public class AccessManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestService requestService;

    public boolean isOwner(Long id){
        Optional<User> user = getOwner();
        return Objects.equals(user.get().getId(), id);
    }

    public boolean isRequestOwner(Long requestId){
        Optional<User> user = getOwner();
        Request request = requestService.getById(requestId);
        return Objects.equals(user.get().getId(), request.getOwner().getId());
    }

    private Optional<User> getOwner() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()) throw new NotFoundException("There are not user with email = " + email);
        return user;
    }

}
