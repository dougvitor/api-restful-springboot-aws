package br.com.home.api.service;

import br.com.home.api.domain.User;
import br.com.home.api.exception.NotFoundException;
import br.com.home.api.model.PageModel;
import br.com.home.api.model.PageRequestModel;
import br.com.home.api.repository.UserRepository;
import br.com.home.api.service.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
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
        Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
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
}
