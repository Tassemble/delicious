package org.tassemble.base.commons.domain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBResource {

    private Connection m_connection;
    private Statement  m_statement;
    private ResultSet  m_resultSet;

    public DBResource(Connection _con, Statement _statement, ResultSet _resultSet) {
        setConnection(_con);
        setStatement(_statement);
        setResultSet(_resultSet);
    }

    public ResultSet getResultSet() {
        return this.m_resultSet;
    }

    public void setResultSet(ResultSet _resultSet) {
        this.m_resultSet = _resultSet;
    }

    public Statement getStatement() {
        return this.m_statement;
    }

    public void setStatement(Statement _statement) {
        this.m_statement = _statement;
    }

    public Connection getConnection() {
        return this.m_connection;
    }

    public void setConnection(Connection _connection) {
        this.m_connection = _connection;
    }
}
