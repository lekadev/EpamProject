package com.mylibrary.service;

import java.sql.*;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Book;
import com.mylibrary.model.Author;
import com.mylibrary.db.ConnectionPool;


public class BookService {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(BookService.class);

    public BookService(ConnectionPool pool) {
        this.pool = pool;
    }

    public int addBook(Book book) {
        String bookQuery = "INSERT INTO library.book(title, publisher, number_copies) VALUES(?, ?, ?)";
        String book2authorQuery = "INSERT INTO library.book2author(id_book, id_author) VALUES(?, ?)";
        Connection connection = null;
        PreparedStatement bookStatement;
        PreparedStatement book2authorStatement;
        ResultSet generatedKey;
        int idBook = 0;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            bookStatement = connection.prepareStatement(bookQuery, Statement.RETURN_GENERATED_KEYS);
            bookStatement.setString(1, book.getTitle());
            bookStatement.setString(2, book.getPublisher());
            bookStatement.setInt(3, book.getNumberCopies());
            bookStatement.executeUpdate();
            generatedKey = bookStatement.getGeneratedKeys();
            if(generatedKey.next()) {
                idBook = generatedKey.getInt(1);
                if(idBook != 0) {
                    book2authorStatement = connection.prepareStatement(book2authorQuery);
                    List<Author> authors = book.getAuthors();
                    for(Author author : authors) {
                        book2authorStatement.setInt(1, idBook);
                        book2authorStatement.setInt(2, author.getId());
                        book2authorStatement.addBatch();
                    }
                    book2authorStatement.executeBatch();
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to execute transaction of book adding", e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                logger.log(Level.ERROR, "Unable to rollback transaction of book adding", e);
            }
        }
        finally {
            pool.closeConnection(connection);
        }
        return idBook;
    }
}
