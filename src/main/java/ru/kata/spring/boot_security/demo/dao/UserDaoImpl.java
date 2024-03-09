package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext()
    private EntityManager entityManager;


    @Override
    public void saveUser(String userName, String password, String email) {
        User user = new User(userName, password, email);
        entityManager.persist(user);
    }

    @Override
    public void save(User user) {

        entityManager.persist(user);
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public void update(User userUpdate) {
        User userToBeUpdate = entityManager.find(User.class, userUpdate.getId());

        if (userToBeUpdate != null) {
            userToBeUpdate.setUserName(userUpdate.getUserName());
            userToBeUpdate.setPassword(userUpdate.getPassword());
            userToBeUpdate.setEmail(userUpdate.getEmail());

            entityManager.merge(userToBeUpdate);
        }
    }

    @Override
    public List<User> findAll() {
        String hql = "SELECT u FROM User u";
        Query query = entityManager.createQuery(hql, User.class);
        return query.getResultList();
    }

    @Override
    public User findByUserName(String userName) {
        String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.userName = :username";
        Query query = entityManager.createQuery(jpql, User.class);
        query.setParameter("username", userName);
        return (User) query.getSingleResult();
    }
}
