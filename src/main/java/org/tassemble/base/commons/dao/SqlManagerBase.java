package org.tassemble.base.commons.dao;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import org.tassemble.base.commons.dao.handler.DBObjectHandler;
import org.tassemble.base.commons.dao.sql.DBListHandler;
import org.tassemble.base.commons.domain.DBResource;


public abstract class SqlManagerBase implements SqlManager {

    public void closeDBResource(DBResource dbr) {
        throw new NoSuchMethodError("该方法在此版本中已经不再被支持");
    }

    public DBResource executeQuery(String sql, List<Object> params) {
        throw new NoSuchMethodError("该方法在此版本中已经不再被支持");
    }

    public DBResource executeQuery(String sql, Object[] params) {
        throw new NoSuchMethodError("该方法在此版本中已经不再被支持");
    }

    public boolean addRecord(String sql, List<Object> paras) {
        throw new NoSuchMethodError("该方法在此版本中已经不再被支持");
    }

    public boolean addRecord(String sql, Object[] paras) {
        throw new NoSuchMethodError("该方法在此版本中已经不再被支持");
    }

    public Connection getConnection() {
        throw new NoSuchMethodError("该方法在此版本中已经不再被支持");
    }

    public String querySingleColInOneRecord(String sql, List<Object> params) {
        throw new NoSuchMethodError("该方法在此版本中已经不再被支持");
    }

    public String querySingleColInOneRecord(String sql, Object[] paras) {
        throw new NoSuchMethodError("该方法在此版本中已经不再被支持");
    }

    public boolean updateRecord(String sql, List<Object> params) {
        throw new NoSuchMethodError("该方法在此版本中已经不再被支持");
    }

    public boolean updateRecord(String sql, Object[] params) {
        throw new NoSuchMethodError("该方法在此版本中已经不再被支持");
    }

    public Long queryCount(String sql, Object[] params) {
        return queryCount(sql, Arrays.asList(params));
    }

    public <T> List<T> queryList(String sql, DBObjectHandler<T> handler, DBListHandler<T> listHandler, Object[] params) {
        return queryList(sql, handler, listHandler, Arrays.asList(params));
    }

    public <T> List<T> queryList(String sql, DBObjectHandler<T> handler, Object[] params) {
        return queryList(sql, handler, Arrays.asList(params));
    }

    public <T> T queryObject(String sql, DBObjectHandler<T> handler, Object[] params) {
        return queryObject(sql, handler, Arrays.asList(params));
    }

    public Long queryObjectId(String sql, Object[] params) {
        return queryObjectId(sql, Arrays.asList(params));
    }

    public Long[] queryObjectIds(String sql, Object[] params) {
        return queryObjectIds(sql, Arrays.asList(params));
    }

    public int updateRecords(String sql, Object[] params) {
        return updateRecords(sql, Arrays.asList(params));
    }

    public boolean existRecord(String sql, Object[] params) {
        return existRecord(sql, Arrays.asList(params));
    }

    public <T> List<T> queryList(String sql, DBObjectHandler<T> handler, List<Object> params) {
        return queryList(sql, handler, null, params);
    }
}