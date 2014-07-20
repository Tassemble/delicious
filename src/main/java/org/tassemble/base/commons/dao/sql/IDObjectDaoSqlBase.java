package org.tassemble.base.commons.dao.sql;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.Assert;
import org.tassemble.base.commons.dao.CommonObjectDao;
import org.tassemble.base.commons.dao.ListCondition;



public abstract class IDObjectDaoSqlBase<DomainObject> extends AbstractDaoSqlBase<DomainObject> implements CommonObjectDao<DomainObject> {

    protected List<DomainObject> queryListOrIds(String sql, List<Object> params) {
        try {
            if (getListOrIds() == 2) {
                sql = sql.replaceFirst("\\*", getObjectConfig().getIdColumn());
                List localList1 = getSqlManager().queryList(sql, this, params);
                return localList1;
            }
            if (getListOrIds() == 3) {
                String columns = getObjectConfig().getIdColumn() + "," + getObjectConfig().getPolicyIdColumn();

                sql = sql.replaceFirst("\\*", (String) columns);
                List localList2 = getSqlManager().queryList(sql, this, params);
                return localList2;
            }
            return getSqlManager().queryList(sql, this, this, params);
        } finally {
            setListOrIds(1);
        }
    }

    protected List<DomainObject> queryListOrIds(String sql, Object... params) {
        return queryListOrIds(sql, Arrays.asList(params));
    }

    protected List<DomainObject> queryCustomListOrIds(ICustomObjectGetter<DomainObject> objectGetter) {
        try {
            List localList = objectGetter.getCustomList();

            return localList;
        } finally {
            setListOrIds(1);
        }
    }

    public boolean update(DomainObject object) {
        Assert.notNull(object);
        Assert.isTrue(getObjectConfig().isHasId(), getObjectConfig().getName() + "对象没有id字段");

        throw new UnsupportedOperationException("整个对象更新较慢，会影响数据库性能，默认不做实现");
    }

    public boolean delete(DomainObject object) {
        Assert.notNull(object);
        Assert.isTrue(getObjectConfig().isHasId(), getObjectConfig().getName() + "对象没有id字段");

        List params = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(getObjectConfig().getTableName());
        sb.append(" where ");
        sb.append(getObjectConfig().getIdColumn());
        sb.append(" =?");
        params.add(Long.valueOf(getObjectConfig().getId(object)));
        if (getObjectConfig().getPolicyIdColumn() != null) {
            sb.append(" and ");
            sb.append(getObjectConfig().getPolicyIdColumn());
            sb.append("=?");
            params.add(getObjectConfig().getPolicyId(object));
        }
        String sql = sb.toString();

        return (getSqlManager().updateRecords(sql, params) <= 1);
    }

    public List<DomainObject> getAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(getObjectConfig().getTableName());
        String sql = sb.toString();

        return queryListOrIds(sql, new Object[0]);
    }

    public List<DomainObject> getAll(ListCondition condition) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(getObjectConfig().getTableName());
        appendListCondition(sb, condition);
        String sql = sb.toString();

        return queryListOrIds(sql, new Object[0]);
    }

    /** @deprecated */
    public Long queryId(String sql, List<Object> params) {
        return getSqlManager().queryObjectId(sql, params);
    }

    /** @deprecated */
    public Long queryId(String sql, Object... params) {
        return getSqlManager().queryObjectId(sql, params);
    }

    /** @deprecated */
    public Long[] queryIds(String _sql, List<Object> _params) {
        setListOrIds(2);
        return getIdsFromList(queryListOrIds(_sql, _params));
    }

    /** @deprecated */
    public Long[] queryIds(String _sql, Object... _params) {
        setListOrIds(2);
        return getIdsFromList(queryListOrIds(_sql, _params));
    }

    protected DomainObject queryObject(String sql, List<Object> _params) {
        return getSqlManager().queryObject(sql, this, _params);
    }

    protected DomainObject queryObject(String sql, Object... params) {
        return queryObject(sql, Arrays.asList(params));
    }

    public List<DomainObject> queryObjects(String _sql, List<Object> _paras) {
        return queryListOrIds(_sql, _paras);
    }

    public List<DomainObject> queryObjects(String _sql, Object... _paras) {
        return queryListOrIds(_sql, _paras);
    }

    private Long[] getIdsFromList(List<DomainObject> idObjects) {
        Long[] ids = new Long[idObjects.size()];
        int i = 0;
        Iterator i$;
        try {
            for (i$ = idObjects.iterator(); i$.hasNext();) {
                Object idObject = i$.next();
                ids[(i++)] = Long.valueOf(Long.parseLong(BeanUtils.getProperty(idObject,
                                                                               getObjectConfig().getIdProperty())));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ids;
    }

    protected Map<Long, DomainObject> queryMap(String sql, List<Object> params) {
        List objects = getSqlManager().queryList(sql, this, this, params);

        Map objMap = new HashMap();
        for (Iterator i$ = objects.iterator(); i$.hasNext();) {
            Object obj = i$.next();
            long id = getObjectConfig().getId(obj);
            objMap.put(Long.valueOf(id), obj);
        }
        return objMap;
    }

    protected Map<Long, DomainObject> queryMap(String sql, Object... params) {
        return queryMap(sql, Arrays.asList(params));
    }
}
