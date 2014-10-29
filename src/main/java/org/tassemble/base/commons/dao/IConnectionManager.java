package org.tassemble.base.commons.dao;

import java.sql.Connection;


public abstract interface IConnectionManager {

    public abstract boolean init();

    public abstract Connection getConnection();

    public abstract long genID(Connection paramConnection, String paramString);
}
