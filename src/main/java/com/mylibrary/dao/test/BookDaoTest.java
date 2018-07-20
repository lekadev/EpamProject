package com.mylibrary.dao.test;

import java.sql.Connection;
import com.mylibrary.entity.Book;
import com.mylibrary.dao.BookDao;
import com.mylibrary.db.ConnectionPool;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class BookDaoTest {

    private Book book;
    private BookDao dao;
    private ConnectionPool pool;
    private Connection connection;

    @org.junit.Before
    public void setUp() throws Exception {
        pool = ConnectionPool.getInstance();
        connection = pool.takeConnection();
        dao = new BookDao(connection);
        book = new Book();
        book.setTitle("someTitle");
        book.setPublisher("somePublisher");
        book.setNumberCopies(1);
    }

    @org.junit.After
    public void tearDown(){
        book = null;
        dao = null;
        pool.closeConnection(connection);
    }

    @org.junit.Test
    public void findAll() throws Exception {
        dao.create(book);
        assertTrue(dao.findAll().size() >= 1);
    }

    @org.junit.Test
    public void findById() throws Exception{
        int idGenerated = dao.create(book);
        assertNotNull(dao.findById(idGenerated));
    }

    @org.junit.Test
    public void create() throws Exception{
        assertTrue(dao.create(book) != 0);
    }

    @org.junit.Test
    public void update() throws Exception {
        int idGenerated = dao.create(book);
        book.setId(idGenerated);
        book.setTitle("newTitle");
        book.setPublisher("newPublisher");
        book.setNumberCopies(2);
        dao.update(book);
        assertEquals(book, dao.findById(book.getId()));
    }
}