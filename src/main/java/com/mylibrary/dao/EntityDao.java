package com.mylibrary.dao;

import java.util.List;
import com.mylibrary.model.Entity;

public abstract class EntityDao<K, T extends Entity> {

    public abstract List<T> findAll();
    public abstract T findById(K id);
    public abstract int deleteById(K id);
    public abstract K create(T entity);
    public abstract int update(T entity);
}
