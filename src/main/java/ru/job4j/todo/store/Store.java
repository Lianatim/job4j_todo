package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface Store {

    Task add(Task task);

    boolean replace(Integer id, Task task);

    boolean delete(Integer id);

    List<Task> findAll();

    List<Task> findByLikeDescription(String key);

    Optional<Task> findById(Integer id);

    List<Task> findByDone(boolean done);

    boolean setDone(Integer id);

    boolean setActive(Integer id);
}
