package com.epam.mylibrary.dao;

import java.sql.Date;
import java.sql.Connection;
import java.util.Random;
import java.util.Calendar;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import com.epam.mylibrary.entity.User;
import com.epam.mylibrary.entity.Reader;
import com.epam.mylibrary.db.ConnectionPool;
import com.epam.mylibrary.dao.exception.DaoException;

public class UserDaoTest {

    private static User user;
    private static UserDao dao;
    private static ConnectionPool pool;
    private static Connection connection;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        user = new Reader();
        user.setEmail("someEmail");
        user.setPassword("somePassword");
        user.setNameFirst("someName");
        user.setNameLast("someSurname");
        user.setRole(User.Role.READER);
        Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
        ((Reader) user).setDateRegistered(currentDate);
        pool = ConnectionPool.getInstance();
        connection = pool.takeConnection();
        dao = new UserDao(connection);
        int id = dao.create(user);
        user.setId(id);
        dao.createReader((Reader) user);
    }

    @AfterClass
    public static void tearDownBeforeClass() {
        user = null;
        dao = null;
    }

    @After
    public void tearDown() {
        pool.closeConnection(connection);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFindAll() {
        dao.findAll();
    }

    @Test
    public void testFindById() throws Exception {
        User foundUser = dao.findById(user.getId());
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
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
        User newUser = new Reader();
        newUser.setEmail("userEmail");
        newUser.setPassword("userPassword");
        newUser.setNameFirst("userName");
        newUser.setNameLast("userSurname");
        newUser.setRole(User.Role.READER);
        Date newDate = new Date(Calendar.getInstance().getTimeInMillis());
        ((Reader) newUser).setDateRegistered(newDate);
        int id = dao.create(newUser);
        User foundUser = dao.findById(id);
        assertNull(foundUser);
        newUser.setId(id);
        dao.createReader((Reader) newUser);
        foundUser = dao.findById(id);
        assertNotNull(foundUser);
        assertEquals(newUser, foundUser);
    }

    @Test
    public void update() throws Exception {
        User foundUser = dao.findById(user.getId());
        assertEquals(user, foundUser);
        foundUser.setNameFirst("newName");
        foundUser.setNameLast("newSurname");
        dao.update(foundUser);
        foundUser = dao.findById(user.getId());
        assertNotEquals(user, foundUser);
        user = foundUser;
    }

    @Test(expected = DaoException.class)
    public void testCreateGivenEmptyObject() throws Exception {
        dao.create(new User());
    }

    @Test
    public void testIsRegistered() throws Exception {
        assertTrue(dao.isRegistered(user.getEmail()));
    }

    @Test
    public void testIsRegisteredGivenInvalidString() throws Exception {
        assertFalse(dao.isRegistered(user.getPassword()));
        assertFalse(dao.isRegistered(new User().getEmail()));
    }

    @Test
    public void findByEmailAndPassword() throws Exception {
        User foundUser = dao.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }

    @Test
    public void testChangePassword() throws Exception {
        User foundUser = dao.findById(user.getId());
        assertEquals(user, foundUser);
        foundUser.setPassword("newPassword");
        dao.changePassword(foundUser);
        foundUser = dao.findById(user.getId());
        assertNotEquals(user, foundUser);
        user = foundUser;
    }

    @Test
    public void testCreateReader() throws Exception {
        User newUser = new Reader();
        newUser.setEmail("readerEmail");
        newUser.setPassword("readerPassword");
        newUser.setNameFirst("readerName");
        newUser.setNameLast("readerSurname");
        newUser.setRole(User.Role.READER);
        Date newDate = new Date(Calendar.getInstance().getTimeInMillis());
        ((Reader) newUser).setDateRegistered(newDate);
        int id = dao.create(newUser);
        User foundUser = dao.findById(id);
        assertNull(foundUser);
        newUser.setId(id);
        dao.createReader((Reader) newUser);
        foundUser = dao.findById(id);
        assertNotNull(foundUser);
        assertEquals(newUser, foundUser);
    }
}