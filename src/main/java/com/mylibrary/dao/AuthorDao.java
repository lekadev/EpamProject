package com.mylibrary.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Author;
import com.mylibrary.db.DBColumns;
import com.mylibrary.db.ConnectionPool;

public class AuthorDao extends EntityDao<Integer, Author> {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(AuthorDao.class);
    private final static String SELECT_ALL = "SELECT id_author, name_first, name_last FROM library.author";
    private final static String SELECT_BY_BOOK_ID = "SELECT id_author, name_first, name_last FROM library.author JOIN library.book2author USING(id_author) WHERE id_book=?";
    private final static String INSERT  = "INSERT INTO library.author(name_first, name_last) VALUES(?, ?)";

    public AuthorDao(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_ALL);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Author author = retrieveAuthor(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of all authors", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return authors;
    }

    @Override
    public Author findById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int deleteById(Integer idAuthor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer create(Author author) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKey = null;
        int idAuthor = 0;
        try{
            connection = pool.takeConnection();
            statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, author.getNameFirst());
            statement.setString(2, author.getNameLast());
            statement.executeUpdate();
            generatedKey = statement.getGeneratedKeys();
            if(generatedKey.next()) {
                idAuthor = generatedKey.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert author", e);
        } finally {
            pool.closeConnection(connection, statement, generatedKey);
        }
        return idAuthor;
    }

    @Override
    public int update(Author author) {
        throw new UnsupportedOperationException();
    }

    public List<Author> findAuthorsOfBook(int idBook) {
        List<Author> authors = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_BY_BOOK_ID);
            statement.setInt(1, idBook);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Author author = retrieveAuthor(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of authors of the book", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return authors;
    }

    private Author retrieveAuthor(ResultSet resultSet) {
        Author author = new Author();
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
