package br.com.home.api.service;

import br.com.home.api.domain.User;
import br.com.home.api.exception.NotFoundException;
import br.com.home.api.model.PageModel;
import br.com.home.api.model.PageRequestModel;
import br.com.home.api.repository.UserRepository;
import br.com.home.api.service.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        user.setPassword(HashUtil.getSecureHash(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User user) {
        user.setPassword(HashUtil.getSecureHash(user.getPassword()));
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("There are not user with id = " + id));
    }

    public Collection<User> listAll() {
        return userRepository.findAll();
    }

    public PageModel<User> listAllOnLazyModel(PageRequestModel pageRequestModel) {
        Pageable pageable = pageRequestModel.toSpringPageRequest();
        final Page<User> page = userRepository.findAll(pageable);
        return new PageModel<>(
                (int) page.getTotalElements(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent()
        );
    }

    public User login(String email, String password) {
        return userRepository.login(email, HashUtil.getSecureHash(password)).get();
    }

    public int updateRole(User user){
        return userRepository.updateRole(user.getId(), user.getRole());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(username);

        if(!result.isPresent()){
            throw new UsernameNotFoundException("Doesn't exist user with email = " + username);
        }

        User user = result.get();

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        org.springframework.security.core.userdetails.User userSpring =
                new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

        return userSpring;
    }
}
