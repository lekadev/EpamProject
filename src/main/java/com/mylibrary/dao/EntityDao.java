package com.mylibrary.dao;

import java.util.List;
import com.mylibrary.entity.Entity;
import com.mylibrary.dao.exception.DaoException;

public abstract class EntityDao<K, T extends Entity> {

    public abstract List<T> findAll() throws DaoException;
    public abstract T findById(K id) throws DaoException;
    public abstract void deleteById(K id) throws DaoException;
    public abstract K create(T entity) throws DaoException;
    public abstract void update(T entity) throws DaoException;
}
