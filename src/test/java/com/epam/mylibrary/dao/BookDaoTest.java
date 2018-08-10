package com.epam.mylibrary.dao;

import org.junit.*;
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
import com.epam.mylibrary.constants.Const;
import com.epam.mylibrary.dao.exception.DaoException;

@RunWith(MockitoJUnitRunner.class)
public class BookDaoTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private BookDao dao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static int someId;
    private static Book book;
    private static List<Book> bookList;

    @BeforeClass
    public static void setUpBeforeClass() {
        book = new Book();
        someId = 1;
        book.setId(someId);
        book.setTitle("title");
        book.setPublisher("publisher");
        book.setNumberCopies(1);
        bookList = Collections.singletonList(book);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
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
        when(resultSet.getInt(1)).thenReturn(book.getId());
        when(resultSet.getInt(Const.BOOK_ID)).thenReturn(book.getId());
        when(resultSet.getString(Const.BOOK_TITLE)).thenReturn(book.getTitle());
        when(resultSet.getString(Const.BOOK_PUBLISHER)).thenReturn(book.getPublisher());
        when(resultSet.getInt(Const.BOOK_NUMBER_COPIES)).thenReturn(book.getNumberCopies());
        doNothing().when(resultSet).close();
    }

    @Test
    public void findAllShouldReturnBookList() throws Exception {
        List<Book> foundList = dao.findAll();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet, times(1)).getInt(Const.BOOK_ID);
        verify(resultSet, times(1)).getString(Const.BOOK_TITLE);
        verify(resultSet, times(1)).getString(Const.BOOK_PUBLISHER);
        verify(resultSet, times(1)).getInt(Const.BOOK_NUMBER_COPIES);
        assertThat(foundList, is(notNullValue()));
        assertThat(foundList.size(), is(greaterThanOrEqualTo(1)));
        assertThat(foundList, hasItem(book));
        assertThat(foundList, is(bookList));
    }

    @Test
    public void findByIdShouldReturnBook() throws Exception {
        Book foundBook = dao.findById(someId);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setInt(1, someId);
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt(Const.BOOK_ID);
        verify(resultSet, times(1)).getString(Const.BOOK_TITLE);
        verify(resultSet, times(1)).getString(Const.BOOK_PUBLISHER);
        verify(resultSet, times(1)).getInt(Const.BOOK_NUMBER_COPIES);
        assertThat(foundBook, is(notNullValue()));
        assertThat(foundBook.getId(), is(book.getId()));
        assertThat(foundBook, is(book));
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
        int generatedId = dao.create(book);
        verify(connection, times(1)).prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS));
        verify(statement, times(1)).setString(1, book.getTitle());
        verify(statement, times(1)).setString(2, book.getPublisher());
        verify(statement, times(1)).setInt(3, book.getNumberCopies());
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).getGeneratedKeys();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt(1);
        assertThat(generatedId, is(book.getId()));
    }

    @Test
    public void createShouldThrowExceptionWhenGivenEmptyBook() throws Exception {
        thrown.expect(DaoException.class);
        dao.create(new Book());
    }

    @Test
    public void updateShouldExecuteUpdate() throws Exception {
        dao.update(book);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setString(1, book.getTitle());
        verify(statement, times(1)).setString(2, book.getPublisher());
        verify(statement, times(1)).setInt(3, book.getNumberCopies());
        verify(statement, times(1)).setInt(4, book.getId());
        verify(statement, times(1)).executeUpdate();
    }
}