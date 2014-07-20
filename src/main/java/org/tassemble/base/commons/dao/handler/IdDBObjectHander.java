package org.tassemble.base.commons.dao.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.netease.framework.dbsupport.callback.DBObjectHandler;

public class IdDBObjectHander implements DBObjectHandler<Long> {

    private static IdDBObjectHander s_instance = new IdDBObjectHander();

    public static IdDBObjectHander getInstance() {
        return s_instance;
    }

    @Override
    public Long handleResultSet(ResultSet arg0) throws SQLException {
        return arg0.getLong("id");
    }

}
