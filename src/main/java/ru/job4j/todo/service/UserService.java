package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> add(User user) {
        return userRepository.add(user);
    }

    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return userRepository.findUserByLoginAndPassword(login, password);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
