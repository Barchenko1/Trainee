package com.nixsolutions.barchenko.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import org.apache.commons.dbcp2.BasicDataSource;

public abstract class AbstractJdbcDao {

    private ResourceBundle resourceBundle;
    private BasicDataSource ds;

    private synchronized BasicDataSource getDataSource() {
        resourceBundle = ResourceBundle.getBundle("h2");
        ds = new BasicDataSource();
        ds.setDriverClassName(resourceBundle.getString("jdbc.driver"));
        ds.setUrl(resourceBundle.getString("jdbc.url"));
        ds.setUsername(resourceBundle.getString("jdbc.username"));
        ds.setPassword(resourceBundle.getString("jdbc.password"));

        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
        return ds;
    }

    protected Connection createConnection() {
        if (ds == null) {
            ds = getDataSource();
        }
        Connection connection;
        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            throw new RuntimeException("creating connection fail", e);
        }
        return connection;
    }


    protected void closeResource(Connection con, PreparedStatement ps,
            Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException("close rs fail", e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException("close ps fail", e);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException("close st fail", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException("close con fail", e);
            }
        }
    }
}
