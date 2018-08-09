package com.epam.mylibrary.dao;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.*;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertThat;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.List;
import java.util.Collections;
import com.epam.mylibrary.entity.Book;
import com.epam.mylibrary.entity.Author;
import com.epam.mylibrary.constants.Const;
import com.epam.mylibrary.db.ConnectionPool;
import com.epam.mylibrary.dao.exception.DaoException;

@RunWith(MockitoJUnitRunner.class)
public class AuthorDaoTest {

    @Mock
    private ConnectionPool pool;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private AuthorDao dao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static int someId;
    private static Author author;
    private static List<Author> authorList;

    @BeforeClass
    public static void setUpBeforeClass() {
        author = new Author();
        someId = 1;
        author.setId(someId);
        author.setNameFirst("name");
        author.setNameLast("surname");
        authorList = Collections.singletonList(author);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(pool.takeConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        doNothing().when(connection).close();
        when(statement.executeQuery()).thenReturn(resultSet);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(statement.executeUpdate()).thenReturn(1);
        doNothing().when(statement).setInt(anyInt(), anyInt());
        doThrow(DaoException.class).when(statement).setInt(anyInt(), intThat(lessThanOrEqualTo(0)));
        doNothing().when(statement).setString(anyInt(), anyString());
        doThrow(DaoException.class).when(statement).setString(anyInt(), isNull(String.class));
        doNothing().when(statement).addBatch();
        when(statement.executeBatch()).thenReturn(null);
        doNothing().when(statement).close();
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt(Const.AUTHOR_ID)).thenReturn(author.getId());
        when(resultSet.getString(Const.AUTHOR_NAME_FIRST)).thenReturn(author.getNameFirst());
        when(resultSet.getString(Const.AUTHOR_NAME_LAST)).thenReturn(author.getNameLast());
        doNothing().when(resultSet).close();
    }

    @Test
    public void findAllShouldReturnAuthorList() throws Exception {
        List<Author> foundList = dao.findAll();
        verify(pool, times(1)).takeConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet, times(1)).getString(Const.AUTHOR_NAME_FIRST);
        verify(resultSet, times(1)).getString(Const.AUTHOR_NAME_LAST);
        assertThat(foundList, is(notNullValue()));
        assertThat(foundList.size(), is(greaterThanOrEqualTo(1)));
        assertThat(foundList, hasItem(author));
        assertThat(foundList, is(authorList));
    }

    @Test
    public void findByIdShouldReturnAuthor() throws Exception {
        Author foundAuthor = dao.findById(someId);
        verify(pool, times(1)).takeConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setInt(anyInt(), eq(someId));
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet, times(1)).getString(Const.AUTHOR_NAME_FIRST);
        verify(resultSet, times(1)).getString(Const.AUTHOR_NAME_LAST);
        assertThat(foundAuthor, is(notNullValue()));
        assertThat(foundAuthor.getId(), is(author.getId()));
        assertThat(foundAuthor, is(author));
    }

    @Test
    public void findByIdShouldThrowExceptionWhenGivenZeroId() throws Exception {
        thrown.expect(DaoException.class);
        dao.findById(0);
    }

    @Test
    public void findByIdShouldThrowExceptionWhenGivenNegativeId() throws Exception {
        thrown.expect(DaoException.class);
        dao.findById(-1);
    }

    @Test
    public void deleteByIdShouldThrowException() {
        thrown.expect(UnsupportedOperationException.class);
        dao.deleteById(someId);
    }

    @Test
    public void createShouldReturnGeneratedId() throws Exception {
        int generatedId = dao.create(author);
        verify(pool, times(1)).takeConnection();
        verify(connection, times(1)).prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS));
        verify(statement, times(1)).setString(1, author.getNameFirst());
        verify(statement, times(1)).setString(2, author.getNameLast());
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).getGeneratedKeys();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt(Const.AUTHOR_ID);
        assertThat(generatedId, is(author.getId()));
    }

    @Test
    public void createShouldThrowExceptionWhenGivenEmptyAuthor() throws Exception {
        thrown.expect(DaoException.class);
        dao.create(new Author());
    }

    @Test
    public void updateShouldThrowException() {
        thrown.expect(UnsupportedOperationException.class);
        dao.update(author);
    }

    @Test
    public void findAuthorsOfBookShouldReturnAuthorList() throws Exception {
        Book book = new Book();
        book.setId(someId);
        book.setAuthors(authorList);
        List<Author> foundList = dao.findAuthorsOfBook(book);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setInt(1, book.getId());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet, times(1)).getString(Const.AUTHOR_NAME_FIRST);
        verify(resultSet, times(1)).getString(Const.AUTHOR_NAME_LAST);
        assertThat(foundList, is(notNullValue()));
        assertThat(foundList.size(), is(greaterThanOrEqualTo(1)));
        assertThat(foundList, hasItem(author));
        assertThat(foundList, is(authorList));
    }

    @Test
    public void findAuthorsOfBookShouldThrowExceptionWhenGivenEmptyBook() throws Exception {
        thrown.expect(DaoException.class);
        dao.findAuthorsOfBook(new Book());
    }

    @Test
    public void insertAuthorsOfBookShouldExecuteInsertion() throws Exception {
        Book book = new Book();
        book.setId(someId);
        book.setAuthors(authorList);
        dao.insertAuthorsOfBook(book);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setInt(1, book.getId());
        verify(statement, times(1)).setInt(2, author.getId());
        verify(statement, times(1)).addBatch();
        verify(statement, times(1)).executeBatch();
    }

    @Test
    public void deleteAuthorsOfBookShouldExecuteDeletion() throws Exception {
        Book book = new Book();
        book.setId(someId);
        book.setAuthors(authorList);
        dao.deleteAuthorsOfBook(book);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setInt(1, book.getId());
        verify(statement, times(1)).executeUpdate();
    }

    @Test
    public void deleteAuthorsOfBookShouldThrowExceptionWhenGivenEmptyBook() throws Exception {
        thrown.expect(DaoException.class);
        dao.deleteAuthorsOfBook(new Book());
    }
}