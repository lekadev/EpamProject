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
import java.util.Calendar;
import com.epam.mylibrary.entity.User;
import com.epam.mylibrary.entity.UserRole;
import com.epam.mylibrary.constants.Const;
import com.epam.mylibrary.db.ConnectionPool;
import com.epam.mylibrary.dao.exception.DaoException;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

    @Mock
    private ConnectionPool pool;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UserDao dao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static int someId;
    private static User user;

    @BeforeClass
    public static void setUpBeforeClass() {
        user = new User();
        someId = 1;
        user.setId(someId);
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(UserRole.READER);
        user.setNameFirst("name");
        user.setNameLast("surname");
        Date someDate = new Date(Calendar.getInstance().getTimeInMillis());
        user.setDateRegistered(someDate);
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
        when(resultSet.getInt(1)).thenReturn(user.getId());
        when(resultSet.getInt(Const.USER_ID)).thenReturn(user.getId());
        when(resultSet.getString(Const.USER_EMAIL)).thenReturn(user.getEmail());
        when(resultSet.getString(Const.USER_PASSWORD)).thenReturn(user.getPassword());
        when(resultSet.getString(Const.USER_ROLE)).thenReturn(String.valueOf(user.getRole()));
        when(resultSet.getString(Const.USER_NAME_FIRST)).thenReturn(user.getNameFirst());
        when(resultSet.getString(Const.USER_NAME_LAST)).thenReturn(user.getNameLast());
        when(resultSet.getDate(Const.USER_DATE_REGISTERED)).thenReturn((Date) user.getDateRegistered());
        doNothing().when(resultSet).close();
    }

    @Test
    public void findAllShouldThrowException() {
        thrown.expect(UnsupportedOperationException.class);
        dao.findAll();
    }

    @Test
    public void findByIdShouldReturnUser() throws Exception {
        User foundUser = dao.findById(user.getId());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setInt(1, someId);
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getString(Const.USER_EMAIL);
        verify(resultSet, times(1)).getString(Const.USER_PASSWORD);
        verify(resultSet, times(1)).getString(Const.USER_ROLE);
        verify(resultSet, times(1)).getString(Const.USER_NAME_FIRST);
        verify(resultSet, times(1)).getString(Const.USER_NAME_LAST);
        verify(resultSet, times(1)).getDate(Const.USER_DATE_REGISTERED);
        assertThat(foundUser, is(notNullValue()));
        assertThat(foundUser.getId(), is(user.getId()));
        assertThat(foundUser, is(user));
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
        int generatedId = dao.create(user);
        verify(pool, times(1)).takeConnection();
        verify(connection, times(1)).prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS));
        verify(statement, times(1)).setString(1, user.getEmail());
        verify(statement, times(1)).setString(2, user.getPassword());
        verify(statement, times(1)).setString(3, String.valueOf(user.getRole()));
        verify(statement, times(1)).setString(4, user.getNameFirst());
        verify(statement, times(1)).setString(5, user.getNameLast());
        verify(statement, times(1)).setDate(6, (Date) user.getDateRegistered());
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).getGeneratedKeys();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt(1);
        assertThat(generatedId, is(user.getId()));
    }

    @Test
    public void createShouldThrowExceptionWhenGivenEmptyUser() throws Exception {
        thrown.expect(DaoException.class);
        dao.create(new User());
    }

    @Test
    public void updateShouldExecuteNameAndSurnameUpdate() throws Exception {
        dao.update(user);
        verify(pool, times(1)).takeConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setString(1, user.getNameFirst());
        verify(statement, times(1)).setString(2, user.getNameLast());
        verify(statement, times(1)).setInt(3, user.getId());
        verify(statement, times(1)).executeUpdate();
    }

    @Test
    public void isRegisteredShouldReturnTrue() throws Exception {
        assertThat(dao.isRegistered(user.getEmail()), is(true));
        verify(pool, times(1)).takeConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setString(1, user.getEmail());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
    }

    @Test
    public void isRegisteredShouldThrowExceptionWhenGivenNullString() throws Exception {
        thrown.expect(DaoException.class);
        dao.isRegistered(null);
    }

    @Test
    public void findByEmailAndPasswordShouldReturnUser() throws Exception {
        User foundUser = dao.findByEmailAndPassword(user.getEmail(), user.getPassword());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setString(1, user.getEmail());
        verify(statement, times(1)).setString(2, user.getPassword());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt(Const.USER_ID);
        verify(resultSet, times(1)).getString(Const.USER_EMAIL);
        verify(resultSet, times(1)).getString(Const.USER_PASSWORD);
        verify(resultSet, times(1)).getString(Const.USER_ROLE);
        verify(resultSet, times(1)).getString(Const.USER_NAME_FIRST);
        verify(resultSet, times(1)).getString(Const.USER_NAME_LAST);
        verify(resultSet, times(1)).getDate(Const.USER_DATE_REGISTERED);
        assertThat(foundUser, is(notNullValue()));
        assertThat(foundUser.getId(), is(user.getId()));
        assertThat(foundUser, is(user));
    }

    @Test
    public void findByEmailAndPasswordShouldThrowExceptionWhenGivenNullString() throws Exception {
        thrown.expect(DaoException.class);
        dao.findByEmailAndPassword(null, null);
    }

    @Test
    public void changePasswordShouldExecutePasswordUpdate() throws Exception {
        dao.changePassword(user);
        verify(pool, times(1)).takeConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setString(1, user.getPassword());
        verify(statement, times(1)).setInt(2, user.getId());
        verify(statement, times(1)).executeUpdate();
    }

    @Test
    public void changePasswordShouldThrowExceptionWhenGivenEmptyUser() throws Exception {
        thrown.expect(DaoException.class);
        dao.changePassword(new User());
    }
}