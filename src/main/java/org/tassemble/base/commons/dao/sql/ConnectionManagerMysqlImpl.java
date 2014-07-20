package org.tassemble.base.commons.dao.sql;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.tassemble.base.commons.dao.IConnectionManager;

public class ConnectionManagerMysqlImpl implements IConnectionManager {

    private BasicDataSource m_dataSource;
    private String          m_driver          = "com.mysql.jdbc.Driver";

    private String          m_user            = null;

    private String          m_pass            = null;

    private String          m_url             = null;

    private int             m_initialSize     = 0;

    private int             m_maxActive       = 10;

    private int             m_maxIdle         = 10;

    private int             m_minIdle         = 0;

    private int             m_maxWait         = -1;

    private String          m_validationQuery = null;

    private boolean         m_testOnBorrow    = false;

    private String          m_idTableName     = "IDGenerator";

    public int getInitialSize() {
        return this.m_initialSize;
    }

    public void setInitialSize(int _initialSize) {
        this.m_initialSize = _initialSize;
    }

    public int getMaxActive() {
        return this.m_maxActive;
    }

    public void setMaxActive(int _maxActive) {
        this.m_maxActive = _maxActive;
    }

    public int getMaxIdle() {
        return this.m_maxIdle;
    }

    public void setMaxIdle(int _maxIdle) {
        this.m_maxIdle = _maxIdle;
    }

    public int getMinIdle() {
        return this.m_minIdle;
    }

    public void setMinIdle(int _minIdle) {
        this.m_minIdle = _minIdle;
    }

    public int getMaxWait() {
        return this.m_maxWait;
    }

    public void setMaxWait(int _maxWait) {
        this.m_maxWait = _maxWait;
    }

    public String getValidationQuery() {
        return this.m_validationQuery;
    }

    public void setValidationQuery(String _validationQuery) {
        this.m_validationQuery = _validationQuery;
    }

    public boolean isTestOnBorrow() {
        return this.m_testOnBorrow;
    }

    public void setTestOnBorrow(boolean _testOnBorrow) {
        this.m_testOnBorrow = _testOnBorrow;
    }

    public String getIdTableName() {
        return this.m_idTableName;
    }

    public void setIdTableName(String _idTableName) {
        this.m_idTableName = _idTableName;
    }

    public String getDriver() {
        return this.m_driver;
    }

    public void setDriver(String _driver) {
        this.m_driver = _driver;
    }

    public String getUser() {
        return this.m_user;
    }

    public void setUser(String _user) {
        this.m_user = _user;
    }

    public String getPass() {
        return this.m_pass;
    }

    public void setPass(String _pass) {
        this.m_pass = _pass;
    }

    public String getUrl() {
        return this.m_url;
    }

    public void setUrl(String _url) {
        this.m_url = _url;
    }

    public BasicDataSource getDataSource() {
        return this.m_dataSource;
    }

    public void setDataSource(BasicDataSource _dataSource) {
        this.m_dataSource = _dataSource;
    }

    public boolean init() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(getDriver());
        ds.setUsername(getUser());
        ds.setPassword(getPass());
        ds.setUrl(getUrl());
        ds.setMaxActive(getMaxActive());
        ds.setMaxIdle(getMaxIdle());
        ds.setMaxWait(getMaxWait());
        ds.setMinIdle(getMinIdle());
        ds.setInitialSize(getInitialSize());
        ds.setValidationQuery(getValidationQuery());
        ds.setTestOnBorrow(isTestOnBorrow());
        setDataSource(ds);
        return true;
    }

    public long genID(Connection _connection, String _tableName) {
        try {
            String sql = "insert into IDGenerator values()";
            Statement stmt = _connection.createStatement();
            stmt.executeUpdate(sql);
            sql = "SELECT LAST_INSERT_ID();";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            long id = rs.getInt(1);
            rs.close();
            stmt.close();
            return id;
        } catch (SQLException e) {
            throw new DBSupportRuntimeException(" Msyql fail to generate ID for table: " + _tableName + " .", e);
        }
    }

    public Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new DBSupportRuntimeException("Fail to get Mysql connection for " + getUrl() + " .", e);
        }
    }
}
