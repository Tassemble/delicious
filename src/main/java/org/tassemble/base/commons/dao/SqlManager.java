package org.tassemble.base.commons.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import org.tassemble.base.commons.dao.handler.DBObjectHandler;
import org.tassemble.base.commons.dao.sql.DBListHandler;
import org.tassemble.base.commons.domain.DBResource;


public abstract interface SqlManager {

    public abstract long allocateRecordId(String paramString);

    public abstract Long queryCount(String paramString, List<Object> paramList);

    public abstract Long queryCount(String paramString, Object... paramArrayOfObject);

    public abstract <T> List<T> queryList(String paramString, DBObjectHandler<T> paramDBObjectHandler,
                                          List<Object> paramList);

    public abstract <T> List<T> queryList(String paramString, DBObjectHandler<T> paramDBObjectHandler,
                                          DBListHandler<T> paramDBListHandler, List<Object> paramList);

    public abstract <T> List<T> queryList(String paramString, DBObjectHandler<T> paramDBObjectHandler,
                                          Object... paramArrayOfObject);

    public abstract <T> List<T> queryList(String paramString, DBObjectHandler<T> paramDBObjectHandler,
                                          DBListHandler<T> paramDBListHandler, Object... paramArrayOfObject);

    public abstract <T> T queryObject(String paramString, DBObjectHandler<T> paramDBObjectHandler,
                                      List<Object> paramList);

    public abstract <T> T queryObject(String paramString, DBObjectHandler<T> paramDBObjectHandler,
                                      Object... paramArrayOfObject);

    public abstract Long queryObjectId(String paramString, List<Object> paramList);

    public abstract Long queryObjectId(String paramString, Object... paramArrayOfObject);

    public abstract Long[] queryObjectIds(String paramString, List<Object> paramList);

    public abstract Long[] queryObjectIds(String paramString, Object... paramArrayOfObject);

    /** @deprecated */
    public abstract boolean updateRecord(String paramString, List<Object> paramList);

    /** @deprecated */
    public abstract boolean updateRecord(String paramString, Object... paramArrayOfObject);

    public abstract int updateRecords(String paramString, List<Object> paramList);

    public abstract int updateRecords(String paramString, Object... paramArrayOfObject);

    public abstract Set<String> getColumns(String paramString);

    /** @deprecated */
    public abstract String querySingleColInOneRecord(String paramString, List<Object> paramList);

    /** @deprecated */
    public abstract String querySingleColInOneRecord(String paramString, Object... paramArrayOfObject);

    /** @deprecated */
    public abstract boolean addRecord(String paramString, List<Object> paramList);

    /** @deprecated */
    public abstract boolean addRecord(String paramString, Object... paramArrayOfObject);

    public abstract boolean existRecord(String paramString, List<Object> paramList);

    public abstract boolean existRecord(String paramString, Object... paramArrayOfObject);

    /** @deprecated */
    public abstract DBResource executeQuery(String paramString, List<Object> paramList);

    /** @deprecated */
    public abstract DBResource executeQuery(String paramString, Object... paramArrayOfObject);

    /** @deprecated */
    public abstract Connection getConnection();

    /** @deprecated */
    public abstract void closeDBResource(DBResource paramDBResource);
}
