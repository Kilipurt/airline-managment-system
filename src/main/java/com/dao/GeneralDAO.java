package com.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public abstract class GeneralDAO<T> {
    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> typeParameterOfClass;

    public void setTypeParameterOfClass(Class<T> typeParameterOfClass) {
        this.typeParameterOfClass = typeParameterOfClass;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public T save(T obj) throws RuntimeException {
        try {
            entityManager.persist(obj);
            return obj;
        } catch (RuntimeException e) {
            throw new RuntimeException("Saving is failed");
        }
    }

    public void delete(long id) throws RuntimeException {
        try {
            entityManager.remove(entityManager.find(typeParameterOfClass, id));
        } catch (RuntimeException e) {
            throw new RuntimeException("Deleting obj " + id + " is failed");
        }
    }

    public T update(T obj) throws RuntimeException {
        try {
            entityManager.merge(obj);
            return obj;
        } catch (RuntimeException e) {
            throw new RuntimeException("Updating is failed");
        }
    }

    public T findById(long id) throws IllegalArgumentException {
        try {
            return entityManager.find(typeParameterOfClass, id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Finding obj " + id + " is failed");
        }
    }
}
