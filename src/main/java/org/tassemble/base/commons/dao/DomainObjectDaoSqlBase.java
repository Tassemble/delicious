package org.tassemble.base.commons.dao;

import java.util.ArrayList;
import java.util.List;

import org.tassemble.base.commons.dao.sql.IDObjectDaoSqlBase;
import org.tassemble.base.commons.dao.utils.ConditionIdsGenerator;
import org.tassemble.base.commons.dao.utils.SortObjects;



public abstract class DomainObjectDaoSqlBase<DomainObject> extends IDObjectDaoSqlBase<DomainObject> implements DomainObjectDao<DomainObject> {

    public boolean deleteById(long id) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(getObjectConfig().getTableName());
        sb.append(" WHERE ");
        sb.append(getObjectConfig().getIdColumn());
        sb.append("=?");
        String sql = sb.toString();

        Object[] params = { Long.valueOf(id) };

        return (getSqlManager().updateRecords(sql, params) == 1);
    }

    /** @deprecated */
    public List<DomainObject> getAllByIds(Long[] ids) {
        return getAllByIds(ids, true);
    }

    public List<DomainObject> getAllByIds(Long[] ids, boolean order) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(getObjectConfig().getTableName());
        sb.append(" WHERE ");
        sb.append(ConditionIdsGenerator.genCondition(ids, getObjectConfig().getIdColumn(), 30));

        String sql = sb.toString();

        List<DomainObject> objects = getSqlManager().queryList(sql, this, this, new Object[0]);
        if (order) {
            objects = SortObjects.sort(ids, objects, getObjectConfig());
        }

        return objects;
    }

    public DomainObject getById(long id) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(getObjectConfig().getTableName());
        sb.append(" WHERE ");
        sb.append(getObjectConfig().getIdColumn());
        sb.append("=?");
        String sql = sb.toString();

        return queryObject(sql, new Object[] { Long.valueOf(id) });
    }

    protected boolean updateColumnCount(long id, String columnName, boolean isInc) {
        int incCount = (isInc) ? 1 : -1;
        return updateColumnCount(id, columnName, incCount);
    }

    protected boolean updateColumnValue(long id, String columnName, Object value) {
        return (updateColumnValues(id, new String[] { columnName }, new Object[] { value }) == 1);
    }

    protected int updateColumnValues(long id, String[] columnNames, Object[] values) {
        if (columnNames.length == 0) {
            return 0;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(getObjectConfig().getTableName());
        sb.append(" SET ");
        appendModifyTime(sb);
        for (int i = 0; i < columnNames.length; ++i) {
            sb.append(columnNames[i]);
            sb.append("=?");
            if (i != columnNames.length - 1) {
                sb.append(",");
            }
        }
        sb.append(" WHERE ");
        sb.append(getObjectConfig().getIdColumn());
        sb.append("=?");
        String sql = sb.toString();

        List params = new ArrayList();
        for (int i = 0; i < columnNames.length; ++i) {
            params.add(values[i]);
        }
        params.add(Long.valueOf(id));

        return getSqlManager().updateRecords(sql, params);
    }

    protected boolean updateColumnCount(long id, String columnName, int incCount) {
        if (incCount == 0) {
            return false;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(getObjectConfig().getTableName());
        sb.append(" SET ");
        appendModifyTime(sb);
        sb.append(columnName);
        sb.append("=");
        sb.append(columnName);
        if (incCount > 0) {
            sb.append("+");
        }
        sb.append(incCount);
        sb.append(" WHERE ");
        sb.append(getObjectConfig().getIdColumn());
        sb.append("=?");
        String sql = sb.toString();
        return (getSqlManager().updateRecords(sql, new Object[] { Long.valueOf(id) }) == 1);
    }
}

