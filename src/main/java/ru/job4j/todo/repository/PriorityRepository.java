package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriorityRepository {

    private final CrudRepository crudRepository;

    public List<Priority> findAll() {
        return crudRepository.query("from Priority", Priority.class);
    }

    public Optional<Priority> findById(int priorityId) {
        return crudRepository.optional(
                "from Priority where id = :fId", Priority.class,
                Map.of("fId", priorityId)
        );
    }
}

