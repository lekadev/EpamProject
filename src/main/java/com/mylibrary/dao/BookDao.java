package com.mylibrary.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Book;
import com.mylibrary.db.DBColumns;
import com.mylibrary.db.ConnectionPool;

public class BookDao extends EntityDao<Integer, Book> {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(BookDao.class);
    private final static String SELECT_ALL = "SELECT id_book, title, publisher, number_copies FROM library.book";
    private final static String SELECT_BY_ID = "SELECT id_book, title, publisher, number_copies FROM library.book WHERE id_book=?";

    public BookDao(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_ALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = retrieveBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of books", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return books;
    }

    @Override
    public Book findById(Integer idBook) {
        Book book = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, idBook);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = retrieveBook(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find book by id", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return book;
    }

    @Override
    public int deleteById(Integer idBook) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer create(Book book) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Book book) {
        throw new UnsupportedOperationException();
    }

    private Book retrieveBook(ResultSet resultSet) {
        Book book = new Book();
        try {
            book.setId(resultSet.getInt(DBColumns.BOOK_ID));
            book.setTitle(resultSet.getString(DBColumns.BOOK_TITLE));
            book.setPublisher(resultSet.getString(DBColumns.BOOK_PUBLISHER));
            book.setNumberCopies(resultSet.getInt(DBColumns.BOOK_NUMBER_COPIES));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to parse result set for book", e);
        }
        return book;
    }
}
