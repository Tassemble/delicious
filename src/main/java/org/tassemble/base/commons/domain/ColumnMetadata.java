package org.tassemble.base.commons.domain;


import java.io.Serializable;

public class ColumnMetadata implements Serializable {

    private static final long serialVersionUID = -5079942731192476162L;
    private String            columnName;
    private int               columnType;
    private String            columnTypeName;
    private String            tableName;
    private String            schemaName;
    private int               displaySize;

    public ColumnMetadata(String name, int type, String typeName, String tableName, String schemaName, int displaySize) {
        this.columnName = name;
        this.columnType = type;
        this.columnTypeName = typeName;
        if (tableName == null) this.tableName = "";
        else this.tableName = tableName;
        this.schemaName = schemaName;
        this.displaySize = displaySize;
    }

    public boolean equals(Object c) {
        if (!(c instanceof ColumnMetadata)) {
            return false;
        }
        ColumnMetadata temp = (ColumnMetadata) c;

        return ((this.columnName.equalsIgnoreCase(temp.columnName)) && (this.tableName.equals(temp.tableName))
                && (this.schemaName.equals(temp.schemaName)) && (this.displaySize == temp.displaySize));
    }

    public String getColumnName() {
        return this.columnName;
    }

    public int getColumnType() {
        return this.columnType;
    }

    public String getColumnTypeName() {
        return this.columnTypeName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public int getDisplaySize() {
        return this.displaySize;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }
}

