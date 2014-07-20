package org.tassemble.base.commons.dao.handler;


import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface DBObjectHandler<T> {

    public abstract T handleResultSet(ResultSet paramResultSet) throws SQLException;
}
