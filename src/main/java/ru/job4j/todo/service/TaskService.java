package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskStore taskStore;

    public TaskService(TaskStore taskStore) {
        this.taskStore = taskStore;
    }

    public Task add(Task task) {
        return taskStore.add(task);
    }

    public boolean replace(Integer id, Task task) {
        return taskStore.replace(id, task);
    }

    public boolean delete(Integer id) {
        return taskStore.delete(id);
    }

    public List<Task> findAll() {
        return taskStore.findAll();
    }

    public List<Task> findByLikeDescription(String key) {
        return taskStore.findByLikeDescription(key);
    }

    public List<Task> findByDone(boolean done) {
        return taskStore.findByDone(done);
    }

    public Optional<Task> findById(Integer id) {
        return taskStore.findById(id);
    }

    public boolean setDone(Integer id) {
        return taskStore.setDone(id);
    }

    public boolean setActive(Integer id) {
        return taskStore.setActive(id);
    }
}
