package com.innova.masraf_takip.Service;

import com.innova.masraf_takip.DataAccessLayer.UserRepository;
import com.innova.masraf_takip.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User saveUser(User User) {
        return userRepository.save(User);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }



}
