package ru.kata.spring.boot_security.demo.dao;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    void saveUser(String userName, String password,  String email);

    public void save(User user);

    User getUserById(Long id);

    void delete(Long id);

    public void update(User userUpdate);

    List<User> findAll();

    User findByUserName(String userName);


}
