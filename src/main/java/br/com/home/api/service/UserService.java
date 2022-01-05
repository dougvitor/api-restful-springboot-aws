package br.com.home.api.service;

import br.com.home.api.domain.User;
import br.com.home.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public User update(User user){
        return userRepository.save(user);
    }

    public User getById(Long id){
        return userRepository.findById(id).get();
    }

    public Collection<User> listAll(){
        return userRepository.findAll();
    }

    public User login(String email, String password){
        return userRepository.login(email, password).get();
    }
}
