package org.tassemble.base.commons.dao.sql;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.tassemble.base.commons.dao.CommonObjectDao;
import org.tassemble.base.commons.dao.ListCondition;
import org.tassemble.base.commons.dao.SqlManager;
import org.tassemble.base.commons.dao.handler.DBObjectHandler;
import org.tassemble.base.commons.dao.sql.annotations.CustomProperty;
import org.tassemble.base.commons.dao.sql.annotations.DataProperty;
import org.tassemble.base.commons.dao.sql.annotations.VirtualProperty;
import org.tassemble.base.commons.domain.ObjectBase;
import org.tassemble.base.commons.dto.ObjectConfig;



public abstract class AbstractDaoSqlBase<DomainObject> implements ObjectConfigInside, DBObjectHandler<DomainObject>, DBListHandler<DomainObject>, CommonObjectDao<DomainObject> {

    private static final Logger              logger        = Logger.getLogger(AbstractDaoSqlBase.class);
    private ObjectConfig                     objectConfig;
    private SqlManager                       sqlManager;
    public static final int                  IDS           = 2;
    public static final int                  IDS_POLICYIDS = 3;
    public static final int                  LIST          = 1;
    private ThreadLocal<Integer>             listOrIds;
    private Map<String, Map<String, Method>> methodMaps;
    private Map<String, Set<String>>         customProperties;

    public AbstractDaoSqlBase() {
        this.listOrIds = new ThreadLocal();

        this.methodMaps = new HashMap();

        this.customProperties = new HashMap();
    }

    public ObjectConfig getObjectConfig() {
        return this.objectConfig;
    }

    public void setObjectConfig(ObjectConfig objectConfig) {
        this.objectConfig = objectConfig;
    }

    /** @deprecated */
    protected long allocateRecordId() {
        return getSqlManager().allocateRecordId(getObjectConfig().getTableName());
    }

    /** @deprecated */
    protected void appendOrderSql(StringBuilder _sb, String _orderColumn, Boolean _isAsc) {
        ListCondition condition = new ListCondition(_orderColumn, _isAsc.booleanValue(), -1, -1L);

        appendListCondition(_sb, condition);
    }

    /** @deprecated */
    protected void appendLimitSql(StringBuilder sb, int _limit, int _offset) {
        ListCondition condition = new ListCondition(_limit, _offset);
        appendListCondition(sb, condition);
    }

    protected void appendModifyTime(StringBuilder sb) {
        if (getObjectConfig().isObjectBase()) {
            sb.append(" ModifyTime=");
            sb.append(System.currentTimeMillis());
            sb.append(",");
        }
    }

    protected void appendListCondition(StringBuilder sb, ListCondition condition) {
        if (condition == null) {
            return;
        }

        if (condition.getOrderColumn() != null) {
            sb.append(" ORDER BY ");
            sb.append(condition.getOrderColumn());

            if (condition.isOrderAsc()) sb.append(" ASC");
            else {
                sb.append(" DESC");
            }
        }

        if (condition.getLimit() > 0) {
            sb.append(" LIMIT ");
            sb.append(condition.getLimit());
        }

        if (condition.getOffset() > 0L) {
            if (condition.getLimit() <= 0) {
                throw new RuntimeException("OFFSET一定要有LIMIT");
            }

            sb.append(" OFFSET ");
            sb.append(condition.getOffset());
        }
    }

    public long getCount() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(*) FROM ");
        sb.append(getObjectConfig().getTableName());
        String sql = sb.toString();

        return getSqlManager().queryCount(sql, new Object[0]).longValue();
    }

    public void deleteAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(getObjectConfig().getTableName());
        String sql = sb.toString();

        getSqlManager().updateRecords(sql, new Object[0]);
    }

    /** @deprecated */
    public SqlManager getSqlSupport() {
        return getSqlManager();
    }

    public SqlManager getSqlManager() {
        return this.sqlManager;
    }

    public void setSqlManager(SqlManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    public int add(DomainObject object) {
        Assert.notNull(object);

        List objects = new ArrayList();
        objects.add(object);

        return add(objects);
    }

    public int add(List<DomainObject> objects) {
        if ((objects == null) || (objects.size() == 0)) {
            return 0;
        }

        List paramMaps = new ArrayList();
        for (Iterator<DomainObject> i$ = objects.iterator(); i$.hasNext();) {
            DomainObject object = i$.next();
            paramMaps.add(getAddParamMap(object));
        }

        boolean isReplace = false;
        if (objects.size() == 1) {
            isReplace = getObjectConfig().isReplace();
        }

        StringBuilder sb = new StringBuilder();

        sb.append(getInsertSql(paramMaps, isReplace));

        if ((objects.size() == 1) && (!(isReplace))) {
            String updateSql = onDeuplicateKeyUpdate(objects.get(0));
            if ((updateSql != null) && (updateSql.trim().length() != 0)) {
                sb.append(" ON DUPLICATE KEY UPDATE ");
                sb.append(updateSql);
            }
        }

        String sql = sb.toString();
        try {
            List params = getParams(paramMaps);
            return getSqlManager().updateRecords(sql, params);
        } catch (DBSupportRuntimeException e) {
            throw handleColumnDoesNotExistException(e, (Map) paramMaps.get(0));
        }
    }

    private RuntimeException handleColumnDoesNotExistException(DBSupportRuntimeException e, Map<String, Object> paramMap) {
        if ("Column does not exist.".endsWith(e.getCause().getMessage())) {
            Set columns = getSqlManager().getColumns(getObjectConfig().getTableName());

            List notExistColumns = new ArrayList();
            for (String column : paramMap.keySet()) {
                if (!(columns.contains(column))) {
                    notExistColumns.add(column);
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Column[");
            sb2.append(StringUtils.join(notExistColumns.iterator(), ','));
            sb2.append("] does not exist.");
            return new RuntimeException(sb2.toString(), e);
        }
        return e;
    }

    private String getInsertSql(List<Map<String, Object>> paramMaps, boolean isReplace) {
        StringBuilder sb = new StringBuilder();
        if (isReplace) sb.append("REPLACE");
        else {
            sb.append("INSERT");
        }
        sb.append(" INTO ");
        sb.append(getObjectConfig().getTableName());
        sb.append("(");
        Map<String, Object> paramMap = (Map) paramMaps.get(0);
        int i = 0;
        for (String columnName : paramMap.keySet()) {
            sb.append(columnName);
            if (i != paramMap.size() - 1) {
                sb.append(",");
            }
            ++i;
        }

        sb.append(") VALUES ");
        String[] questions = new String[paramMap.size()];
        Arrays.fill(questions, "?");
        String[] inserts = new String[paramMaps.size()];
        Arrays.fill(inserts, "(" + StringUtils.join(questions, ",") + ")");
        sb.append(StringUtils.join(inserts, ","));
        return sb.toString();
    }

    private List<Object> getParams(List<Map<String, Object>> paramMaps) {
        List params = new ArrayList();
        for (Iterator i$ = paramMaps.iterator(); i$.hasNext();) {
            Map<String, Object> pm = (Map) i$.next();
            for (String columnName : pm.keySet())
                params.add(pm.get(columnName));
        }
        Map pm;
        return params;
    }

    protected String onDeuplicateKeyUpdate(DomainObject object) {
        return null;
    }

    protected AddParamMap getAddParamMap(DomainObject object) {
        try {
            AddParamMap paramMap = new AddParamMap();

            Class clazz = Class.forName(getObjectConfig().getClassName());
            Method[] methods = clazz.getMethods();

            Map<String, Method> methodMap = new HashMap();
            for (Method method : methods) {
                if (method.isAnnotationPresent(VirtualProperty.class)) continue;
                if (method.getName().equals("getClass")) {
                    continue;
                }

                if (method.isAnnotationPresent(DataProperty.class)) {
                    DataProperty dp = (DataProperty) method.getAnnotation(DataProperty.class);
                    methodMap.put(dp.column(), method);
                } else if ((method.getName().startsWith("is")) && (method.getParameterTypes().length == 0)) {
                    String name = method.getName().substring(2);
                    methodMap.put(name, method);
                } else {
                    if ((!(method.getName().startsWith("get"))) || (method.getParameterTypes().length != 0)) continue;
                    String name = method.getName().substring(3);
                    methodMap.put(name, method);
                }
            }

            for (String name : methodMap.keySet()) {
                Method method = (Method) methodMap.get(name);
                Object value = method.invoke(object, new Object[0]);
                if ((getObjectConfig().isIdColumn(name))
                    && (((value == null) || (value.equals(Integer.valueOf(0))) || (value.equals(Long.valueOf(0L))) || (value.equals(Short.valueOf((short) 0)))))) {
                    setObjectId(object);
                    value = method.invoke(object, new Object[0]);
                }

                paramMap.put(name, value);
            }

            if (object instanceof ObjectBase) {
                long now = System.currentTimeMillis();
                paramMap.put("CreateTime", Long.valueOf(now));
                paramMap.put("ModifyTime", Long.valueOf(now));
            }

            return paramMap;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    protected void setObjectId(DomainObject _object) {
        Assert.isTrue(getObjectConfig().isHasId(), getObjectConfig().getName() + "对象没有id字段");

        long id = getSqlManager().allocateRecordId(getObjectConfig().getTableName());
        try {
            BeanUtils.setProperty(_object, getObjectConfig().getIdProperty(), Long.valueOf(id));
        } catch (Exception e) {
            throw new RuntimeException("object" + _object + "不存在名为id的property", e);
        }
    }

    public void handDBList(List<DomainObject> objs) {
    }

    public final DomainObject handleResultSet(ResultSet _rs) throws SQLException {
        if (getListOrIds() == 1) return getObjectFromRs(_rs);
        if (getListOrIds() == 2) {
            if ((logger.isDebugEnabled()) && (_rs.getMetaData().getColumnCount() > 1)) {
                logger.error("取ID的SQL返回的结果不止1列。", new Error());
            }

            ObjectConfig config = getObjectConfig();

            long id = _rs.getLong(config.getIdColumn());

            String className = config.getClassName();
            try {
                Object object = Class.forName(className).newInstance();

                BeanUtils.setProperty(object, config.getIdProperty(), Long.valueOf(id));
                return (DomainObject)object;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (getListOrIds() == 3) {
            if ((logger.isDebugEnabled()) && (_rs.getMetaData().getColumnCount() != 2)) {
                logger.error("取ID的SQL返回的结果不是2列。", new Error());
            }

            ObjectConfig config = getObjectConfig();

            long id = _rs.getLong(config.getIdColumn());
            Object policyId = _rs.getObject(config.getPolicyIdColumn());

            String className = config.getClassName();
            try {
                Object object = Class.forName(className).newInstance();

                BeanUtils.setProperty(object, config.getIdProperty(), Long.valueOf(id));
                BeanUtils.setProperty(object, config.getPolicyIdProperty(), policyId);

                return (DomainObject)object;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException(getListOrIds() + "是错误的ListOrIds标识！");
    }

    public DomainObject getObjectFromRs(ResultSet rs) throws SQLException {
        try {
            String className = getObjectConfig().getClassName();
            Map methodMap = getMethodMap(className);
            Set properties = getCustomProperties(className);
            Object object = newObject(className);
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount();
            for (int i = 1; i <= count; ++i) {
                String columnName = metaData.getColumnName(i).toLowerCase();
                if (properties.contains(columnName)) continue;
                if (methodMap.containsKey(columnName)) {
                    Object value = rs.getObject(columnName);
                    if (value != null) {
                        Class valueType = value.getClass();

                        Method method = (Method) methodMap.get(columnName);
                        Class propertyType = method.getParameterTypes()[0];
                        propertyType = ClassUtils.primitiveToWrapper(propertyType);

                        boolean support = true;
                        if (!(valueType.equals(propertyType))) {
                            if (valueType.equals(Long.class)) {
                                if (propertyType.equals(Integer.class)) value = Integer.valueOf(((Long) value).intValue());
                                else if (propertyType.equals(Short.class)) value = Short.valueOf(((Long) value).shortValue());
                                else if (propertyType.equals(Byte.class)) value = Byte.valueOf(((Long) value).byteValue());
                                else if (propertyType.equals(String.class)) value = String.valueOf(value);
                                else support = false;
                            } else if (valueType.equals(Integer.class)) {
                                if (propertyType.equals(Long.class)) value = Long.valueOf(((Integer) value).longValue());
                                else if (propertyType.equals(Short.class)) value = Short.valueOf(((Integer) value).shortValue());
                                else if (propertyType.equals(Byte.class)) value = Byte.valueOf(((Integer) value).byteValue());
                                else if (propertyType.equals(String.class)) value = String.valueOf(value);
                                else if (propertyType.equals(Boolean.class)) value = Boolean.valueOf(((Integer) value).intValue() != 0);
                                else support = false;
                            } else if (valueType.equals(String.class)) try {
                                if (propertyType.equals(Long.class)) value = Long.valueOf(Long.parseLong((String) value));
                                else if (propertyType.equals(Integer.class)) {
                                    value = Integer.valueOf(Integer.parseInt((String) value));
                                } else if (propertyType.equals(Short.class)) {
                                    value = Short.valueOf(Short.parseShort((String) value));
                                } else if (propertyType.equals(Byte.class)) value = Byte.valueOf(Byte.parseByte((String) value));
                                else support = false;
                            } catch (NumberFormatException e) {
                                logger.warn("String转化错误", e);
                                support = false;
                            }
                            else if (valueType.equals(Timestamp.class)) {
                                if (propertyType.equals(String.class)) value = value.toString();
                                else support = false;
                            } else if (valueType.equals(Date.class)) {
                                if (propertyType.equals(String.class)) value = value.toString();
                                else support = false;
                            } else if (valueType.equals(Time.class)) {
                                if (propertyType.equals(String.class)) value = value.toString();
                                else support = false;
                            } else {
                                support = false;
                            }
                        }
                        if (support) method.invoke(object, new Object[] { value });
                        else {
                            logger.warn("暂时不支持" + valueType + "到" + propertyType + "的转化", new RuntimeException());
                        }
                    }
                } else {
                    logger.warn(columnName + "没有找到对应的set方法");
                }
            }

            return (DomainObject)object;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private DomainObject newObject(String className) {
        try {
            Class clazz = Class.forName(className);
            Object object = clazz.newInstance();
            return (DomainObject)object;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public int getListOrIds() {
        Integer flag = (Integer) this.listOrIds.get();
        return ((flag == null) ? 1 : flag.intValue());
    }

    public void setListOrIds(int listOrIds) {
        this.listOrIds.set(Integer.valueOf(listOrIds));
    }

    private Set<String> getCustomProperties(String className) {
        Set cp = (Set) this.customProperties.get(className);
        if (cp == null) {
            synchronized (this) {
                cp = (Set) this.customProperties.get(className);
                if (cp == null) {
                    init(className);
                    cp = (Set) this.customProperties.get(className);
                }
            }
        }
        return cp;
    }

    private Map<String, Method> getMethodMap(String className) {
        Map methodMap = (Map) this.methodMaps.get(className);
        if (methodMap == null) {
            synchronized (this) {
                methodMap = (Map) this.methodMaps.get(className);
                if (methodMap == null) {
                    init(className);
                    methodMap = (Map) this.methodMaps.get(className);
                }
            }
        }
        return methodMap;
    }

    private void init(String className) {
        try {
            Class clazz = Class.forName(className);

            Method[] methods = clazz.getMethods();
            Map methodMap = new HashMap();
            Set customProperty = new HashSet();

            for (Method method : methods) {
                if (method.isAnnotationPresent(CustomProperty.class)) {
                    String propertyName = getPropertyName(method, CustomProperty.class.getSimpleName());

                    customProperty.add(propertyName.toLowerCase());
                } else if (method.isAnnotationPresent(DataProperty.class)) {
                    DataProperty dp = (DataProperty) method.getAnnotation(DataProperty.class);
                    String columnName = dp.column();
                    String methodName = getPropertyName(method, DataProperty.class.getSimpleName());

                    methodName = "set" + methodName;
                    method = clazz.getMethod(methodName, new Class[] { method.getReturnType() });

                    methodMap.put(columnName.toLowerCase(), method);
                } else {
                    if ((!(method.getName().startsWith("set"))) || (method.getParameterTypes().length != 1)) continue;
                    String name = method.getName().substring(3).toLowerCase();
                    methodMap.put(name, method);
                }
            }
            this.methodMaps.put(className, methodMap);
            this.customProperties.put(className, customProperty);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPropertyName(Method method, String annotationName) {
        if ((method.getName().startsWith("get")) || (method.getName().startsWith("set"))) {
            return method.getName().substring(3);
        }
        if (method.getName().startsWith("is")) {
            return method.getName().substring(2);
        }
        throw new RuntimeException(annotationName + "标识标错位置了");
    }
}
