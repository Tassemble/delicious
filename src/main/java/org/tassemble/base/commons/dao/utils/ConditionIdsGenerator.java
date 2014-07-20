package org.tassemble.base.commons.dao.utils;

import org.springframework.util.Assert;

public class ConditionIdsGenerator {

    public static String genCondition(Long[] ids, String column, int step) {
        Assert.notNull(column, "列名不能为null");

        column = column.trim();
        Assert.isTrue(column.length() != 0, "列名不能为空");

        Assert.isTrue(step >= 2, "step必须大于等于2");

        if ((ids == null) || (ids.length == 0)) {
            return "";
        }

        if (ids.length > 1000) {
            new Error("select ids length too long,don't worry,just a warning").printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        genCondition(ids, column, 0, ids.length, step, sb);
        return sb.toString();
    }

    public static String genCondition(Long[] ids, String idColumn, Object[] policyIds, String policyIdColumn, int step) {
        Assert.notNull(idColumn, "ID列名不能为null");
        idColumn = idColumn.trim();
        Assert.isTrue(idColumn.length() != 0, "ID列名不能为空");

        Assert.notNull(policyIdColumn, "均衡字段列名不能为null");
        policyIdColumn = policyIdColumn.trim();
        Assert.isTrue(policyIdColumn.length() != 0, "均衡字段列名不能为空");

        Assert.isTrue(step >= 2, "step必须大于等于2");

        if ((ids == null) || (policyIds == null) || (ids.length == 0)) {
            return "";
        }

        if (ids.length != policyIds.length) {
            throw new IllegalArgumentException("ID列表和均衡字段列表必须相等。");
        }

        if (ids.length > 1000) {
            new Error("select ids length too long").printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        genCondition(ids, idColumn, policyIds, policyIdColumn, 0, ids.length, step, sb);

        return sb.toString();
    }

    private static void genCondition(Long[] ids, String column, int beginIndex, int endIndex, int step, StringBuilder sb) {
        sb.append("(");
        int length = endIndex - beginIndex;
        if (length <= step) {
            for (int i = beginIndex; i < endIndex; ++i) {
                sb.append(column);
                sb.append("=");
                sb.append(ids[i]);
                if (i != endIndex - 1) sb.append(" OR ");
            }
        } else {
            int subLength = length / step;
            int longSub = length % step;
            int bIndex = beginIndex;
            int eIndex = bIndex;
            for (int i = 0; i < step; ++i) {
                if (longSub > i) eIndex += subLength + 1;
                else {
                    eIndex += subLength;
                }
                genCondition(ids, column, bIndex, eIndex, step, sb);
                if (i != step - 1) {
                    sb.append(" OR ");
                }
                bIndex = eIndex;
            }
        }
        sb.append(")");
    }

    private static void genCondition(Long[] ids, String idColumn, Object[] policyIds, String policyIdColumn,
                                     int beginIndex, int endIndex, int step, StringBuilder sb) {
        sb.append("(");
        int length = endIndex - beginIndex;
        if (length <= step) {
            for (int i = beginIndex; i < endIndex; ++i) {
                sb.append("(");
                sb.append(idColumn);
                sb.append("=");
                sb.append(ids[i]);
                sb.append(" AND ");
                sb.append(policyIdColumn);
                sb.append("='");
                sb.append(policyIds[i]);
                sb.append("')");
                if (i != endIndex - 1) sb.append(" OR ");
            }
        } else {
            int subLength = length / step;
            int longSub = length % step;
            int bIndex = beginIndex;
            int eIndex = bIndex;
            for (int i = 0; i < step; ++i) {
                if (longSub > i) eIndex += subLength + 1;
                else {
                    eIndex += subLength;
                }
                genCondition(ids, idColumn, policyIds, policyIdColumn, bIndex, eIndex, step, sb);

                if (i != step - 1) {
                    sb.append(" OR ");
                }
                bIndex = eIndex;
            }
        }
        sb.append(")");
    }
}

