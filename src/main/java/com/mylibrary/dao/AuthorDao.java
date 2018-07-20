package com.mylibrary.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.entity.Book;
import com.mylibrary.entity.Author;
import com.mylibrary.db.DBColumns;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.dao.exception.DaoException;

public class AuthorDao extends EntityDao<Integer, Author> {

    private final static Logger logger = Logger.getLogger(AuthorDao.class);

    private final static String SELECT_ALL = "SELECT id_author, name_first, name_last FROM library.author";
    private final static String SELECT_BY_ID = "SELECT id_author, name_first, name_last FROM library.author WHERE id_author=?";
    private final static String SELECT_AUTHORS_OF_BOOK = "SELECT id_author, name_first, name_last FROM library.author JOIN library.book2author USING(id_author) WHERE id_book=?";
    private final static String INSERT_NEW_AUTHOR = "INSERT INTO library.author(name_first, name_last) VALUES(?, ?)";
    private final static String INSERT_AUTHOR_OF_BOOK = "INSERT INTO library.book2author(id_book, id_author) VALUES(?, ?)";
    private final static String DELETE_AUTHOR_OF_BOOK = "DELETE FROM library.book2author WHERE id_book=?";

    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection connection;

    public AuthorDao() { }

    public AuthorDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Author> findAll() throws DaoException {
        List<Author> authors = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_ALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Author author = retrieveAuthor(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of all authors", e);
            throw new DaoException();
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return authors;
    }

    @Override
    public Author findById(Integer idAuthor) throws DaoException {
        Author author = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, idAuthor);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                author = retrieveAuthor(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to select author by id", e);
            throw new DaoException();
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return author;
    }

    @Override
    public void deleteById(Integer idAuthor) throws DaoException {
        throw new DaoException(new UnsupportedOperationException());
    }

    @Override
    public Integer create(Author author) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKey = null;
        int idAuthor = 0;
        try{
            connection = pool.takeConnection();
            statement = connection.prepareStatement(INSERT_NEW_AUTHOR, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, author.getNameFirst());
            statement.setString(2, author.getNameLast());
            statement.executeUpdate();
            generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                idAuthor = generatedKey.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to create author", e);
            throw new DaoException();
        } finally {
            pool.closeConnection(connection, statement, generatedKey);
        }
        return idAuthor;
    }

    @Override
    public void update(Author author) throws DaoException {
        throw new DaoException(new UnsupportedOperationException());
    }

    public List<Author> findAuthorsOfBook(Book book) throws DaoException {
        List<Author> authors = new ArrayList<>();
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(SELECT_AUTHORS_OF_BOOK);
            statement.setInt(1, book.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Author author = retrieveAuthor(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of authors", e);
            throw new DaoException();
        }
        return authors;
    }

    public void insertAuthorsOfBook(Book book) throws DaoException {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(INSERT_AUTHOR_OF_BOOK);
            for (Author author : book.getAuthors()) {
                statement.setInt(1, book.getId());
                statement.setInt(2, author.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert authors", e);
            throw  new DaoException();
        }
    }

    public void deleteAuthorsOfBook(Book book) throws DaoException {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(DELETE_AUTHOR_OF_BOOK);
            statement.setInt(1, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete authors", e);
            throw new DaoException();
        }
    }

    private Author retrieveAuthor(ResultSet resultSet) throws DaoException {
        Author author = new Author();
        try{
            author.setId(resultSet.getInt(DBColumns.AUTHOR_ID));
            author.setNameFirst(resultSet.getString(DBColumns.AUTHOR_NAME_FIRST));
            author.setNameLast(resultSet.getString(DBColumns.AUTHOR_NAME_LAST));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to parse result set", e);
            throw new DaoException();
        }
        return author;
    }
}
