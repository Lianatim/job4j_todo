package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.Optional;

@Service
public class UserService {

    private final UserStore userStore;

    public UserService(UserStore userStore) {
        this.userStore = userStore;
    }

    public Optional<User> add(User user) {
        return userStore.add(user);
    }

    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return userStore.findUserByLoginAndPassword(login, password);
    }

    public Optional<User> findById(int id) {
        return userStore.findById(id);
    }
}
