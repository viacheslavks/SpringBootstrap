package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {


    void saveUser(String userName, String password,  String email);

    public void save(User user);

    User getUserById(long id);

    void delete(long id);

    public void update(User userUpdate);

    List<User> findAll();
    User findByUserName(String userName);



}
