package com.epam.mylibrary.dao;

import java.sql.Date;
import java.sql.Connection;
import java.util.Random;
import java.util.Calendar;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import com.epam.mylibrary.entity.User;
import com.epam.mylibrary.entity.UserRole;
import com.epam.mylibrary.db.ConnectionPool;
import com.epam.mylibrary.dao.exception.DaoException;

public class UserDaoTest {

    /*private static User user;
    private static UserDao dao;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        user = new User();
        user.setEmail("someEmail");
        user.setPassword("somePassword");
        user.setRole(UserRole.READER);
        user.setNameFirst("someName");
        user.setNameLast("someSurname");
        Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
        user.setDateRegistered(currentDate);
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        dao = new UserDao(connection);
        int id = dao.create(user);
        user.setId(id);
    }

    @AfterClass
    public static void tearDownBeforeClass() {
        user = null;
        dao = null;
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFindAll() {
        dao.findAll();
    }

    @Test
    public void testFindById() throws Exception {
        User foundUser = dao.findById(user.getId());
        assertThat(foundUser, is(notNullValue()));
        assertThat(foundUser, is(user));
    }

    @Test
    public void testFindByIdGivenZero() throws Exception {
        int idZero = 0;
        User foundUser = dao.findById(idZero);
        assertThat(foundUser, is(nullValue()));
    }

    @Test
    public void testFindByIdGivenInvalidNumber() throws Exception {
        int idNegative = -1;
        User foundUser = dao.findById(idNegative);
        assertThat(foundUser, is(nullValue()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDeleteById() {
        int id = new Random().nextInt();
        dao.deleteById(id);
    }

    @Test
    public void testCreate() throws Exception {
        User newUser = new User();
        newUser.setEmail("userEmail");
        newUser.setPassword("userPassword");
        newUser.setRole(UserRole.READER);
        newUser.setNameFirst("userName");
        newUser.setNameLast("userSurname");
        Date newDate = new Date(Calendar.getInstance().getTimeInMillis());
        newUser.setDateRegistered(newDate);
        int id = dao.create(newUser);
        assertThat(id, is(not(0)));
        User foundUser = dao.findById(id);
        assertThat(foundUser, is(notNullValue()));
        assertThat(foundUser, is(newUser));
    }

    @Test(expected = DaoException.class)
    public void testCreateGivenEmptyObject() throws Exception {
        dao.create(new User());
    }

    @Test
    public void update() throws Exception {
        User foundUser = dao.findById(user.getId());
        assertThat(foundUser, is(user));
        foundUser.setNameFirst("newName");
        foundUser.setNameLast("newSurname");
        dao.update(foundUser);
        foundUser = dao.findById(user.getId());
        assertThat(foundUser, is(not(user)));
        user = foundUser;
    }

    @Test
    public void testIsRegistered() throws Exception {
        String email = user.getEmail();
        assertThat(dao.isRegistered(email), is(true));
    }

    @Test
    public void testIsRegisteredGivenEmptyString() throws Exception {
        String email = new User().getEmail();
        assertThat(dao.isRegistered(email), is(false));
    }

    @Test
    public void findByEmailAndPassword() throws Exception {
        User foundUser = dao.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(foundUser, is(notNullValue()));
        assertThat(foundUser, is(user));
    }

    @Test
    public void testChangePassword() throws Exception {
        User foundUser = dao.findById(user.getId());
        assertThat(foundUser, is(user));
        foundUser.setPassword("newPassword");
        dao.changePassword(foundUser);
        foundUser = dao.findById(user.getId());
        assertThat(foundUser, is(not(user)));
        user = foundUser;
    }*/
}