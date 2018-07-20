package com.mylibrary.dao.test;

import com.mylibrary.model.Author;
import com.mylibrary.dao.AuthorDao;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertNotNull;

public class AuthorDaoTest {

    private Author author;
    private AuthorDao dao;

    @org.junit.Before
    public void setUp() {
        dao = new AuthorDao();
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
    public void findById() throws Exception {
        int idGenerated = dao.create(author);
        assertNotNull(dao.findById(idGenerated));
    }

    @org.junit.Test
    public void create() throws Exception {
        assertTrue(dao.create(author) != 0);
    }
}