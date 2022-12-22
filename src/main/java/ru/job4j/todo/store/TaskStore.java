package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore implements Store {
    private final SessionFactory sf;

    @Override
    public Task add(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        task.setCreated(LocalDateTime.now());
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    @Override
    public boolean replace(Integer id, Task task) {
        Session session = sf.openSession();
        int rsl = 0;
        try {
            session.beginTransaction();
            rsl = session.createQuery("UPDATE Task SET description = :fDescription, created = :fCreated, done = :fDone   WHERE id = :fId")
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fCreated", LocalDateTime.now())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return rsl > 0;
    }

    @Override
    public boolean delete(Integer id) {
        Session session = sf.openSession();
        int rsl = 0;
        try {
            session.beginTransaction();
            rsl = session.createQuery("DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return rsl > 0;
    }

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery("from Task");
        List<Task> rsl = new ArrayList<>(query.list());
        session.close();
        return rsl;
    }

    @Override
    public List<Task> findByLikeDescription(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<Task> query = session.createQuery("from Task where description like :fKey", Task.class);
        List<Task> rsl = query.setParameter("fKey", "%" + key + "%").list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery("from Task as t where t.id = :fId", Task.class);
        query.setParameter("fId", id);
        Optional<Task> rsl = query.uniqueResultOptional();
        session.close();
        return rsl;
    }

    @Override
    public List<Task> findByDone() {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<Task> query = session.createQuery("from Task as t where t.done = :fDone", Task.class);
        List<Task> rsl = query.setParameter("fDone", true).list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public List<Task> findByActive() {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<Task> query = session.createQuery("from Task as t where t.done = :fDone", Task.class);
        List<Task> rsl = query.setParameter("fDone", false).list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public boolean setDone(Integer id) {
        Session session = sf.openSession();
        int rsl = 0;
        try {
            session.beginTransaction();
            rsl = session.createQuery("UPDATE Task as t SET t.done = :fDone WHERE t.id = :fId")
                    .setParameter("fDone", true)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return rsl > 0;
    }

    @Override
    public boolean setActive(Integer id) {
        Session session = sf.openSession();
        int rsl = 0;
        try {
            session.beginTransaction();
            rsl = session.createQuery("UPDATE Task as t SET t.done = :fDone WHERE t.id = :fId")
                    .setParameter("fDone", false)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return rsl > 0;
    }
}