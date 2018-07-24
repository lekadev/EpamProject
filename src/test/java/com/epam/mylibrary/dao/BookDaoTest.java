package com.epam.mylibrary.dao;

import java.util.List;
import java.util.Random;
import java.sql.Connection;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import com.epam.mylibrary.entity.Book;
import com.epam.mylibrary.db.ConnectionPool;
import com.epam.mylibrary.dao.exception.DaoException;

public class BookDaoTest {

    private static Book book;
    private static BookDao dao;
    private static Random random;
    private static ConnectionPool pool;
    private static Connection connection;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        book = new Book();
        book.setTitle("someTitle");
        book.setPublisher("somePublisher");
        book.setNumberCopies(1);
        random = new Random();
        pool = ConnectionPool.getInstance();
        connection = pool.takeConnection();
        dao = new BookDao(connection);
        int id = dao.create(book);
        book.setId(id);
    }

    @AfterClass
    public static void tearDownBeforeClass() {
        book = null;
        dao = null;
    }

    @After
    public void tearDown() {
        pool.closeConnection(connection);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Book> books = dao.findAll();
        assertTrue(books.size() >= 1);
        assertTrue(books.contains(book));
    }

    @Test
    public void testFindById() throws Exception {
        Book foundBook = dao.findById(book.getId());
        assertNotNull(foundBook);
        assertEquals(book, foundBook);
    }

    @Test
    public void testFindByIdGivenInvalidNumber() throws Exception {
        int idZero = 0;
        int idNegative = -1;
        assertNull(dao.findById(idZero));
        assertNull(dao.findById(idNegative));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteById() {
        int id = random.nextInt();
        dao.deleteById(id);
    }

    @Test
    public void testCreate() throws Exception {
        Book newBook = new Book();
        newBook.setTitle("bookTitle");
        newBook.setPublisher("bookPublisher");
        newBook.setNumberCopies(random.nextInt());
        int id = dao.create(newBook);
        assertTrue(id != 0);
        Book foundBook = dao.findById(id);
        assertNotNull(foundBook);
        assertEquals(newBook, foundBook);
    }

    @Test(expected = DaoException.class)
    public void testCreateGivenEmptyObject() throws Exception {
        dao.create(new Book());
    }

    @Test
    public void testUpdate() throws Exception {
        Book foundBook = dao.findById(book.getId());
        assertEquals(book, foundBook);
        foundBook.setTitle("newTitle");
        foundBook.setPublisher("newPublisher");
        foundBook.setNumberCopies(random.nextInt());
        dao.update(foundBook);
        foundBook = dao.findById(book.getId());
        assertNotEquals(book, foundBook);
    }
}