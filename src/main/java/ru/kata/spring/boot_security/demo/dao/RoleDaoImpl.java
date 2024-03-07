package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext()
    private EntityManager entityManager;


    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role getRole(String userName) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name =:name", Role.class)
                .setParameter("name", userName).getSingleResult();
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.id =:id", Role.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }
}
