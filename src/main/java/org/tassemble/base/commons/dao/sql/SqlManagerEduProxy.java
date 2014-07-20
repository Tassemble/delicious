package org.tassemble.base.commons.dao.sql;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tassemble.base.commons.constants.CommonConstants;
import org.tassemble.base.commons.dao.SqlManager;
import org.tassemble.base.commons.dao.handler.DBObjectHandler;
import org.tassemble.base.commons.dao.transaction.SqlManagerImpl;
import org.tassemble.base.commons.domain.DBResource;
import org.tassemble.base.commons.utils.log.holder.LogInfo;
import org.tassemble.base.commons.utils.log.holder.LogInfoHolder;

public class SqlManagerEduProxy extends SqlManagerImpl implements SqlManager {

    private static final Log performLog            = LogFactory.getLog(CommonConstants.PERFORM_LOG_NAME);
    private static final Log frequentCalledBeanLog = LogFactory.getLog(CommonConstants.FREQUENT_CALLED_BEAN_LOG);

    @Override
    public DBResource executeQuery(String sql, List<Object> params) {

        boolean logPerform = performLog.isInfoEnabled();
        long startMilli = 0;
        if (logPerform) {
            startMilli = System.currentTimeMillis();

        }

        DBResource dbr = super.executeQuery(sql, params);

        if (logPerform) {
            LogInfo logInfo = LogInfoHolder.getLogInfo();
            if (logInfo != null && logInfo.isFrequentCalledBean()) {
                frequentCalledBeanLog.info("calling sql:" + sql + " params: " + params + " using: "
                                           + (System.currentTimeMillis() - startMilli) + " ms. ");
            } else {
                performLog.info("calling sql:" + sql + " params: " + params + " using: "
                                + (System.currentTimeMillis() - startMilli) + " ms. ");
            }

        }

        return dbr;
    }

    @Override
    public int updateRecords(String sql, List<Object> params) {

        boolean logPerform = performLog.isInfoEnabled();
        long startMilli = 0;
        if (logPerform) {
            startMilli = System.currentTimeMillis();

        }

        int cnt = super.updateRecords(sql, params);

        if (logPerform) {
            LogInfo logInfo = LogInfoHolder.getLogInfo();
            if (logInfo != null && logInfo.isFrequentCalledBean()) {
                frequentCalledBeanLog.info("calling sql:" + sql + " params: " + params + " using: "
                                           + (System.currentTimeMillis() - startMilli) + " ms. ");
            } else {
                performLog.info("calling sql:" + sql + " params: " + params + " using: "
                                + (System.currentTimeMillis() - startMilli) + " ms. ");
            }

        }

        return cnt;
    }

}
