package com.mylibrary.dao;

import java.sql.*;
import java.util.Map;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Book;
import com.mylibrary.db.DBColumns;
import com.mylibrary.db.ConnectionPool;
import java.util.concurrent.ConcurrentHashMap;

public class BookDao {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(BookDao.class);

    private final static String SELECT_ALL = "SELECT * FROM library.book";
    private final static String SELECT_BY_ID = "SELECT * FROM library.book WHERE id_book=?";

    public BookDao(ConnectionPool pool) {
        this.pool = pool;
    }

    public Map<Integer, Book> findAllBooks() {
        String sql = SELECT_ALL;
        Map<Integer, Book> books = new ConcurrentHashMap<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = retrieveBook(resultSet);
                books.put(book.getId(), book);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of books", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return books;
    }

    public int create(Book book){
        String bookSQl = "INSERT INTO library.book(title, publisher, number_copies) VALUES(?, ?, ?)";
        String book2AuthorSQl = "INSERT INTO library.book2author(id_book, id_author) VALUES(?, ?)";
        Connection connection = null;
        PreparedStatement bookStatement = null;
        PreparedStatement book2AuthorStatement = null;
        ResultSet generatedKey = null;
        int idBook = 0;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            bookStatement = connection.prepareStatement(bookSQl, PreparedStatement.RETURN_GENERATED_KEYS);
            bookStatement.setString(1, book.getTitle());
            bookStatement.setString(2, book.getPublisher());
            bookStatement.setInt(3, book.getNumberCopies());
            bookStatement.executeUpdate();
            generatedKey = bookStatement.getGeneratedKeys();
            int generatedId = 0;
            if(generatedKey.next()) {
                generatedId = generatedKey.getInt(1);
                if(generatedId != 0) {
                    book2AuthorStatement = connection.prepareStatement(book2AuthorSQl);
                    List<Book.Author> authors = book.getAuthors();
                    for(Book.Author author : authors) {
                        book2AuthorStatement.setInt(1, generatedId);
                        book2AuthorStatement.setInt(2, author.getId());
                        book2AuthorStatement.addBatch();
                    }
                    book2AuthorStatement.executeBatch();
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            idBook = generatedId;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to execute new book insertion transaction", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.log(Level.ERROR, "Unable to roll back new book insertion transaction", e);
            }
        } finally {
            pool.closeConnection(connection);
        }
        return idBook;
    }

    public Book findBookById(int id) {
        String sql = SELECT_BY_ID;
        Book book = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                book = retrieveBook(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find book by id", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return book;
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
