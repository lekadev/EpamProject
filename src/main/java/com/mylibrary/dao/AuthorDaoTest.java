package com.mylibrary.dao;

import com.mylibrary.db.ConnectionPool;
import com.mylibrary.model.Author;

import static junit.framework.TestCase.assertTrue;

public class AuthorDaoTest {

    private Author author;
    private AuthorDao dao;

    @org.junit.Before
    public void setUp() {
        ConnectionPool pool = ConnectionPool.getInstance();
        dao = new AuthorDao(pool);
        author = new Author();
        author.setNameFirst("someName");
        author.setNameLast("someSurname");
    }

    @org.junit.After
    public void tearDown() {
        author = null;
        dao = null;
    }

    @org.junit.Test
    public void findAll() throws Exception {
        dao.create(author);
        assertTrue(dao.findAll().size() >= 1);
    }

    @org.junit.Test
    public void findById() {
    }

    @org.junit.Test
    public void deleteById() {
    }

    @org.junit.Test
    public void create() {
    }

    @org.junit.Test
    public void update() {
    }
}