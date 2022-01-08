package br.com.home.api.service;

import br.com.home.api.domain.User;
import br.com.home.api.repository.UserRepository;
import br.com.home.api.service.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
        return userRepository.findById(id).get();
    }

    public Collection<User> listAll() {
        return userRepository.findAll();
    }

    public User login(String email, String password) {
        return userRepository.login(email, HashUtil.getSecureHash(password)).get();
    }
}
