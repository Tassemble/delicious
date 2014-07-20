package org.tassemble.base.commons.dao.sql;

import java.sql.SQLException;

public class DBSupportRuntimeException extends RuntimeException {

    private static final long serialVersionUID                    = 4557980479639111220L;
    public static final int   SQLParseError                       = 11001;
    public static final int   UnsupportedCharFoundInSQL           = 11002;
    public static final int   IllegalSQLPattern                   = 11003;
    public static final int   IllegalSpecialChar                  = 11004;
    public static final int   EmptyToken                          = 11005;
    public static final int   IllegalCharFoundInDecimal           = 11006;
    public static final int   IllegalEcapeCharLength              = 11007;
    public static final int   IllegalSQLTokenType                 = 11008;
    public static final int   NoFromTokenFoundInSELECTCommand     = 11009;
    public static final int   IllegalIdentifier                   = 11010;
    public static final int   ColumnCountDoesNotMatch             = 11011;
    public static final int   TableNotFound                       = 11012;
    public static final int   OperationConflictsWithDataMigration = 18001;

    public int getSQLErrorCode() {
        return ((SQLException) getCause()).getErrorCode();
    }

    public String getSQLErrorMessage() {
        return ((SQLException) getCause()).getMessage();
    }

    public DBSupportRuntimeException() {
    }

    public DBSupportRuntimeException(String message) {
        super(message);
    }

    public DBSupportRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBSupportRuntimeException(String message, SQLException cause) {
        super(message, cause);
    }

    public DBSupportRuntimeException(SQLException cause) {
        super(cause);
    }

    public DBSupportRuntimeException(Throwable cause) {
        super(cause);
    }
}
