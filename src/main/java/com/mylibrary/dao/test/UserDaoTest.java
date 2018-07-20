package com.mylibrary.dao.test;

import static org.junit.Assert.*;
import com.mylibrary.dao.UserDao;
import com.mylibrary.model.User;
import com.mylibrary.model.Librarian;


public class UserDaoTest {

    private User user;
    private UserDao dao;

    @org.junit.Before
    public void setUp() {
        user = new Librarian();
        user.setEmail("email@mail.ru");
        user.setPassword("password");
        user.setNameFirst("someName");
        user.setNameLast("someSurname");
        user.setRole(User.Role.LIBRARIAN);
        ((Librarian)user).setNumberPhone("111");
        dao = new UserDao();
    }

    @org.junit.After
    public void tearDown() {
        user = null;
        dao = null;
    }

    @org.junit.Test
    public void update() throws Exception {
        int idGenerated = dao.create(user);
        user.setId(idGenerated);
        user.setNameFirst("newName");
        user.setNameLast("newSurname");
        ((Librarian)user).setNumberPhone("222");
        dao.update(user);
        assertEquals(user, dao.findById(user.getId()));
    }

    @org.junit.Test
    public void isRegistered() throws Exception {
        dao.create(user);
        assertTrue(dao.isRegistered(user.getEmail()));
    }

    @org.junit.Test
    public void findByEmailAndPassword() throws Exception {
        dao.create(user);
        assertNotNull(dao.findByEmailAndPassword(user.getEmail(), user.getPassword()));
    }

    @org.junit.Test
    public void changePassword() throws Exception {
        int idGenerated = dao.create(user);
        user.setId(idGenerated);
        user.setPassword("newPassword");
        dao.changePassword(user);
        assertEquals(user, dao.findById(user.getId()));
    }
}