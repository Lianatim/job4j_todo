package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task add(Task task) {
        return taskRepository.add(task);
    }

    public boolean replace(Integer id, Task task) {
        return taskRepository.replace(id, task);
    }

    public boolean delete(Integer id) {
        return taskRepository.delete(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findByLikeDescription(String key) {
        return taskRepository.findByLikeDescription(key);
    }

    public List<Task> findByDone(boolean done) {
        return taskRepository.findByDone(done);
    }

    public Optional<Task> findById(Integer id) {
        return taskRepository.findById(id);
    }

    public boolean setDone(Integer id) {
        return taskRepository.setDone(id);
    }

    public boolean setActive(Integer id) {
        return taskRepository.setActive(id);
    }
}
