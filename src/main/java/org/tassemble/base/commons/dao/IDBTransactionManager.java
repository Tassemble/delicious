package org.tassemble.base.commons.dao;

import java.sql.Connection;


public abstract interface IDBTransactionManager {

    public abstract Connection getConnection();

    public abstract void releaseConnection();

    public abstract void setConnection(Connection paramConnection);

    public abstract boolean getAutoCommit();

    public abstract void setAutoCommit(boolean paramBoolean);

    public abstract void commit();

    public abstract void rollback();

    public abstract void clear();
}
