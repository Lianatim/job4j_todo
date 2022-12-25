package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;


@Repository
@AllArgsConstructor
public class UserStore {

    private final SessionFactory sf;

    public Optional<User> add(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return Optional.of(user);
    }

    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery("from User as u where u.login = :fLogin AND u.password = :fPassword", User.class);
        query.setParameter("fLogin", login);
        query.setParameter("fPassword", password);
        Optional<User> rsl = query.uniqueResultOptional();
        session.close();
        return rsl;
    }

    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery("from User as u where u.id = :fId", User.class);
        query.setParameter("fId", id);
        Optional<User> rsl = query.uniqueResultOptional();
        session.close();
        return rsl;
    }

}
