package com.epam.mylibrary.dao;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import com.epam.mylibrary.constants.Const;
import com.epam.mylibrary.db.ConnectionPool;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import com.epam.mylibrary.entity.Author;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthorDaoTest {

    private int someId = 1;
    private Author author = new Author("name", "surname");
    private List<Author> authorsList = Arrays.asList(author);

    @Mock
    ConnectionPool pool;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement statement;

    @Mock
    ResultSet resultSet;

    @InjectMocks
    private AuthorDao dao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(pool.takeConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(connection.prepareStatement(eq(anyString()), Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        doNothing().when(connection).close();
        when(statement.executeQuery()).thenReturn(resultSet);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(statement.executeUpdate()).thenReturn(1);
        doNothing().when(statement).setInt(anyInt(), anyInt());
        doNothing().when(statement).setString(anyInt(), anyString());
        doNothing().when(statement).addBatch();
        when(statement.executeBatch()).thenReturn(null);
        doNothing().when(statement).close();
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt(anyInt())).thenReturn(someId);
        when(resultSet.getString(Const.AUTHOR_NAME_FIRST)).thenReturn(author.getNameFirst());
        when(resultSet.getString(Const.AUTHOR_NAME_LAST)).thenReturn(author.getNameLast());
        doNothing().when(resultSet).close();
    }

    @Test
    public void findAllShouldReturnAuthorsList() throws Exception {
        List<Author> foundList = dao.findAll();
        verify(pool, times(1)).takeConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(4)).next();
        verify(resultSet, times(2)).getString(Const.AUTHOR_NAME_FIRST);
        verify(resultSet, times(2)).getString(Const.AUTHOR_NAME_LAST);
        assertThat(foundList, is(authorsList));
    }

    /*@Test
    public void testFindById() throws Exception {

    }

    @Test
    public void testFindByIdGivenZero() throws Exception {

    }

    @Test
    public void testFindByIdGivenNegativeNumber() throws Exception {

    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDeleteById() {

    }

    @Test
    public void testCreate() throws Exception {

    }

    @Test(expected = DaoException.class)
    public void testCreateGivenEmptyObject() throws Exception {

    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdate() {

    }

    @Test(expected = NullPointerException.class)
    public void testFindAuthorsOfBook() throws Exception {

    }

    @Test(expected = NullPointerException.class)
    public void testInsertAuthorsOfBook() throws Exception {

    }

    @Test(expected = NullPointerException.class)
    public void testDeleteAuthorsOfBook() throws Exception {

    }*/
}