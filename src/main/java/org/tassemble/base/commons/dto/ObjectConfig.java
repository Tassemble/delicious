package org.tassemble.base.commons.dto;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.tassemble.base.commons.domain.ObjectBase;

public class ObjectConfig {

    private String          name;
    private String          className;
    private String          tableName;
    private String          idProperty;
    private String          idColumn;
    private String          policyIdProperty;
    private String          policyIdColumn;
    private boolean         isReplace;
    private boolean         hasId;
    private boolean         hasEvent;
    private boolean         isObjectBase;
    private boolean         isInitObjectBase;

    public ObjectConfig() {
        this.idProperty = "id";

        this.isReplace = false;


        this.hasId = true;

        this.hasEvent = true;
    }

    public boolean isHasEvent() {
        return this.hasEvent;
    }

    public void setHasEvent(boolean hasEvent) {
        this.hasEvent = hasEvent;
    }

    public boolean isHasId() {
        return this.hasId;
    }

    public void setHasId(boolean hasId) {
        this.hasId = hasId;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIdProperty() {
        return this.idProperty;
    }

    public void setIdProperty(String keyName) {
        this.idProperty = keyName;
    }

    public String getName() {
        if (this.name == null) {
            return getClassName().substring(getClassName().lastIndexOf(46) + 1);
        }

        return this.name;
    }

    public String getPolicyIdProperty() {
        return this.policyIdProperty;
    }

    public void setPolicyIdProperty(String policyField) {
        this.policyIdProperty = policyField;
    }

    public long getId(Object _object) {
        try {
            return Long.parseLong(BeanUtils.getProperty(_object, getIdProperty()));
        } catch (Exception e) {
            throw new RuntimeException(_object + "没有" + getIdProperty() + "属性", e);
        }
    }

    public Object getPolicyId(Object _object) {
        try {
            return PropertyUtils.getProperty(_object, getPolicyIdProperty());
        } catch (Exception e) {
            throw new RuntimeException(_object + "没有" + getPolicyIdProperty() + "属性", e);
        }
    }


    public String getPolicyIdColumn() {
        if (this.policyIdColumn == null) {
            return getPolicyIdProperty();
        }
        return this.policyIdColumn;
    }

    public void setPolicyIdColumn(String _policyIdColumn) {
        this.policyIdColumn = _policyIdColumn;
    }

    public String getTableName() {
        if (this.tableName == null) {
            return getName();
        }
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdColumn() {
        if (this.idColumn == null) {
            return getIdProperty();
        }
        return this.idColumn;
    }

    public void setIdColumn(String idColumn) {
        this.idColumn = idColumn;
    }

    /** @deprecated */
    public void setKeyColumn(String idColumn) {
        setIdColumn(idColumn);
    }

    /** @deprecated */
    public String getKeyColumn() {
        return getIdColumn();
    }

    public boolean isIdColumn(String columnName) {
        return ((isHasId()) && (getIdColumn().equalsIgnoreCase(columnName)));
    }

    public boolean isReplace() {
        return this.isReplace;
    }

    public void setReplace(boolean isReplace) {
        this.isReplace = isReplace;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** @deprecated */
    public void setKeyField(String name) {
        setIdProperty(name);
    }

    /** @deprecated */
    public void setPolicyColumn(String policyColumn) {
        setPolicyIdColumn(policyColumn);
    }

    /** @deprecated */
    public void setPolicyField(String policyField) {
        setPolicyIdProperty(policyField);
    }

    public boolean isObjectBase() {
        if (!(this.isInitObjectBase)) {
            String key = "isInitObjectBase";
            synchronized (key) {
                if (!(this.isInitObjectBase)) {
                    try {
                        Class clazz = Class.forName(getClassName());
                        Object obj = clazz.newInstance();
                        if (obj instanceof ObjectBase) this.isObjectBase = true;
                        else {
                            this.isObjectBase = false;
                        }
                        this.isInitObjectBase = true;
                    } catch (Exception e) {
                        new RuntimeException(e);
                    }
                }
            }
        }
        return this.isObjectBase;
    }
}
