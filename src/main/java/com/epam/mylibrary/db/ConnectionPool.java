package com.epam.mylibrary.db;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;


public class ConnectionPool {

    private static final Logger logger = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool poolInstance = null;
    private static volatile boolean isCreated = false;

    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> givenAwayConnections;

    private String password;
    private int poolSize;
    private String login;
    private String url;

    public static ConnectionPool getInstance() {
        if (!isCreated) {
            synchronized (ConnectionPool.class) {
                poolInstance = new ConnectionPool();
                isCreated = true;
                logger.log(Level.INFO, "Connection pool was initialized");
            }
        }
        return poolInstance;
    }

    private ConnectionPool() {
        ResourceBundle bundle = ResourceBundle.getBundle(DBConfig.DB_FILENAME);
        this.poolSize = Integer.parseInt(bundle.getString(DBConfig.DB_POOL_SIZE));
        this.url = bundle.getString(DBConfig.DB_URL);
        this.login = bundle.getString(DBConfig.DB_LOGIN);
        this.password = bundle.getString(DBConfig.DB_PASSWORD);
        initPoolData();
    }

    private void initPoolData() {
        availableConnections = new ArrayBlockingQueue<>(poolSize);
        givenAwayConnections = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, login, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                availableConnections.add(pooledConnection);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Unable to initialize connection pool", e);
            }
        }
    }

    public Connection takeConnection() throws SQLException {
        Connection connection;
        try {
            connection = availableConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Unable to allocate connection", e);
            throw new SQLException();
        }

        return connection;
    }

    public void closeConnection(Connection con, PreparedStatement pst, ResultSet rs) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to return connection to the pool", e);
        }
        try {
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to close statement", e);
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to close result set", e);
        }
    }

    public void closeConnection(Connection con, PreparedStatement pst) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to return connection to the pool", e);
        }
        try {
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to close statement", e);
        }
    }

    public void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to return connection to the pool", e);
        }
    }

    public void dispose() {
        disposeConnectionsQueue(availableConnections);
        disposeConnectionsQueue(givenAwayConnections);
    }

    private void disposeConnectionsQueue(BlockingQueue<Connection> queue) {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            try {
                connection.commit();
                ((PooledConnection) connection).reallyClose();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Unable to close connection", e);
            }
        }
    }

    private class PooledConnection implements Connection {

        private Connection connection;

        PooledConnection(Connection connection) throws SQLException {
            this.connection = connection;
            this.connection.setAutoCommit(true);
        }

        private void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Connection has already been closed.");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!givenAwayConnections.remove(this)) {
                throw new SQLException("Unable to remove connection from the queue of given away connections.");
            }
            if (!availableConnections.offer(this)) {
                throw new SQLException("Unable to return used connection to the queue of available connections");
            }
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }
    }
}
