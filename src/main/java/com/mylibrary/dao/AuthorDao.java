package com.mylibrary.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Book;
import com.mylibrary.db.DBColumns;
import com.mylibrary.db.ConnectionPool;

public class AuthorDao {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(AuthorDao.class);

    private final static String SELECT_ALL = "SELECT * FROM library.author";
    private final static String SELECT_BY_BOOKID = "SELECT * FROM library.author JOIN library.book2author USING(id_author) WHERE id_book=?";
    private final static String INSERT  = "INSERT INTO library.author(name_first, name_last) VALUES(?, ?)";

    public AuthorDao(ConnectionPool pool) {
        this.pool = pool;
    }

    public List<Book.Author> findAuthorsOfBook(int idBook) {
        String sql = SELECT_BY_BOOKID;
        List<Book.Author> authors = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idBook);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Book.Author author = retrieveAuthor(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of authors of the book", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return authors;
    }

    public List<Book.Author> findAll() {
        String sql = SELECT_ALL;
        List<Book.Author> authors = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Book.Author author = retrieveAuthor(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of all authors", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return authors;
    }

    public int create(Book.Author author) {
        String sql = INSERT;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKey = null;
        int id = 0;
        try{
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, author.getNameFirst());
            statement.setString(2, author.getNameLast());
            statement.executeUpdate();
            generatedKey = statement.getGeneratedKeys();
            if(generatedKey.next()) {
                id = generatedKey.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert author", e);
        } finally {
            pool.closeConnection(connection, statement, generatedKey);
        }
        return id;
    }

    private Book.Author retrieveAuthor(ResultSet resultSet) {
        Book.Author author = new Book.Author();
        try{
            author.setId(resultSet.getInt(DBColumns.AUTHOR_ID));
            author.setNameFirst(resultSet.getString(DBColumns.AUTHOR_NAME_FIRST));
            author.setNameLast(resultSet.getString(DBColumns.AUTHOR_NAME_LAST));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to parse result set for author", e);
        }
        return author;
    }
}
