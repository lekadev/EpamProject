package com.epam.mylibrary.dao;

import java.util.List;
import java.util.Random;
import java.sql.Connection;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import com.epam.mylibrary.entity.Book;
import com.epam.mylibrary.db.ConnectionPool;
import com.epam.mylibrary.dao.exception.DaoException;

public class BookDaoTest {

    /*private static Book book;
    private static BookDao dao;
    private static Random random;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        book = new Book();
        book.setTitle("someTitle");
        book.setPublisher("somePublisher");
        book.setNumberCopies(1);
        random = new Random();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        dao = new BookDao(connection);
        int id = dao.create(book);
        book.setId(id);
    }

    @AfterClass
    public static void tearDownBeforeClass() {
        book = null;
        dao = null;
    }

    @Test
    public void testFindAll() throws Exception {
        List<Book> books = dao.findAll();
        assertThat(books, is(notNullValue()));
        assertThat(books.size(), greaterThanOrEqualTo(1));
        assertThat(books, hasItem(book));
    }

    @Test
    public void testFindById() throws Exception {
        Book foundBook = dao.findById(book.getId());
        assertThat(foundBook, is(notNullValue()));
        assertThat(foundBook, is(book));
    }

    @Test
    public void testFindByIdGivenZero() throws Exception {
        int idZero = 0;
        Book foundBook = dao.findById(idZero);
        assertThat(foundBook, is(nullValue()));
    }

    @Test
    public void testFindByIdNegativeNumber() throws Exception {
        int idNegative = -1;
        Book foundBook = dao.findById(idNegative);
        assertThat(foundBook, is(nullValue()));
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
        assertThat(id, is(not(0)));
        Book foundBook = dao.findById(id);
        assertThat(foundBook, is(notNullValue()));
        assertThat(foundBook, is(newBook));
    }

    @Test(expected = DaoException.class)
    public void testCreateGivenEmptyObject() throws Exception {
        dao.create(new Book());
    }

    @Test
    public void testUpdate() throws Exception {
        Book foundBook = dao.findById(book.getId());
        assertThat(foundBook, is(book));
        foundBook.setTitle("newTitle");
        foundBook.setPublisher("newPublisher");
        foundBook.setNumberCopies(random.nextInt());
        dao.update(foundBook);
        foundBook = dao.findById(book.getId());
        assertThat(foundBook, is(not(book)));
        book = foundBook;
    }*/
}