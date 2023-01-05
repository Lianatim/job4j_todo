package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param task задача.
     * @return задача с id.
     */
    public Task add(Task task) {
        task.setCreated(LocalDateTime.now());
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    /**
     * Обновить в базе задачу.
     *
     * @param task задача.
     */
    public boolean replace(Integer id, Task task) {
        return crudRepository.booleanQuery(
                "UPDATE Task SET description = :fDescription, created = :fCreated, done = :fDone   WHERE id = :fId",
                Map.of("fDescription", task.getDescription(),
                        "fCreated", LocalDateTime.now(),
                        "fDone", task.isDone(),
                        "fId", id)
        );
    }

    /**
     * Удалить задачу по id.
     *
     * @param taskId ID
     */
    public boolean delete(int taskId) {
        return crudRepository.booleanQuery(
                "DELETE FROM Task where id = :fId",
                Map.of("fId", taskId)
        );
    }

    /**
     * Список всех задач
     *
     * @return список задач.
     */
    public List<Task> findAll() {
        return crudRepository.query("FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories", Task.class);
    }

    /**
     * Найти задачу по ID
     *
     * @return задача.
     */
    public Optional<Task> findById(int taskId) {
        return crudRepository.optional(
                "FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories where t.id = :fId", Task.class,
                Map.of("fId", taskId)
        );
    }

    /**
     * Список задач по description LIKE %key%
     * @param key key
     * @return список задач.
     */
    public List<Task> findByLikeDescription(String key) {
        return crudRepository.query(
                "FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories where description t.like :fKey", Task.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Список задач по условию выполненные или активные.
     * @param done done
     * @return список задач по условию выполненные или активные.
     */
    public List<Task> findByDone(boolean done) {
        return crudRepository.query("FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories where t.done = :fDone", Task.class,
                Map.of("fDone", done)
        );
    }

    /**
     * Установить статус "выполнено" задачи по id.
     *
     * @param taskId ID
     */
    public boolean setDone(int taskId) {
        return crudRepository.booleanQuery(
                "UPDATE Task as t SET t.done = :fDone WHERE t.id = :fId",
                Map.of("fDone", true, "fId", taskId)
        );
    }


    /**
     * Установить статус "активно" задачи по id.
     *
     * @param taskId ID
     */
    public boolean setActive(int taskId) {
        return crudRepository.booleanQuery(
                "UPDATE Task as t SET t.done = :fDone WHERE t.id = :fId",
                Map.of("fDone", false, "fId", taskId)
        );
    }
}
