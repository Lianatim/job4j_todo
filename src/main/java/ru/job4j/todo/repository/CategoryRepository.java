package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepository {

    private final CrudRepository crudRepository;

    public List<Category> findAll() {
        return crudRepository.query("from Category", Category.class);
    }

    public Optional<Category> findById(int categoryId) {
        return crudRepository.optional(
                "from Category where id = :fId", Category.class,
                Map.of("fId", categoryId)
        );
    }
}
