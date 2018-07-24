package com.epam.mylibrary.dao;

import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import com.epam.mylibrary.entity.Book;
import com.epam.mylibrary.entity.Author;
import com.epam.mylibrary.dao.exception.DaoException;

public class AuthorDaoTest {

    private static Author author;
    private static AuthorDao dao;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
       author = new Author("someName", "someSurname");
       dao = new AuthorDao();
       int id = dao.create(author);
       author.setId(id);
    }

    @AfterClass
    public static void tearDownBeforeClass() {
        author = null;
        dao = null;
    }

    @Test
    public void testFindAll() throws Exception {
        List<Author> authors = dao.findAll();
        assertTrue(authors.size() >= 1);
        assertTrue(authors.contains(author));
    }

    @Test
    public void testFindById() throws Exception {
        Author foundAuthor = dao.findById(author.getId());
        assertNotNull(foundAuthor);
        assertEquals(author, foundAuthor);
    }

    @Test
    public void testFindByIdGivenInvalidNumber() throws Exception {
        int idZero = 0;
        int idNegative = -1;
        assertNull(dao.findById(idZero));
        assertNull(dao.findById(idNegative));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDeleteById() {
        int id = new Random().nextInt();
        dao.deleteById(id);
    }

    @Test
    public void testCreate() throws Exception {
        Author newAuthor = new Author("authorName", "authorSurname");
        int id = dao.create(newAuthor);
        Author foundAuthor = dao.findById(id);
        assertNotNull(foundAuthor);
        assertEquals(newAuthor, foundAuthor);
    }

    @Test(expected = DaoException.class)
    public void testCreateGivenEmptyObject() throws Exception {
        dao.create(new Author());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdate() {
        dao.update(author);
    }

    @Test(expected = NullPointerException.class)
    public void testFindAuthorsOfBook() throws Exception {
        Book book = new Book();
        dao.findAuthorsOfBook(book);
    }

    @Test(expected = NullPointerException.class)
    public void testInsertAuthorsOfBook() throws Exception {
        Book book = new Book();
        dao.insertAuthorsOfBook(book);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteAuthorsOfBook() throws Exception {
        Book book = new Book();
        dao.deleteAuthorsOfBook(book);
    }
}