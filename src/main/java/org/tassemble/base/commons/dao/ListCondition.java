package org.tassemble.base.commons.dao;

public class ListCondition  {

    private String  orderColumn;
    private boolean orderAsc;
    private int     limit;
    private long    offset;

    public ListCondition(String orderColumn, boolean orderAsc) {
        this(orderColumn, orderAsc, -1, -1L);
    }

    public ListCondition(String orderColumn, boolean orderAsc, int limit, long offset) {
        setOrderColumn(orderColumn);
        setOrderAsc(orderAsc);
        setLimit(limit);
        setOffset(offset);
    }

    public ListCondition(int limit, long offset) {
        this(null, false, limit, offset);
    }

    public int getLimit() {
        return this.limit;
    }

    private void setLimit(int limit) {
        this.limit = limit;
    }

    public long getOffset() {
        return this.offset;
    }

    private void setOffset(long offset) {
        this.offset = offset;
    }

    public boolean isOrderAsc() {
        return this.orderAsc;
    }

    private void setOrderAsc(boolean orderAsc) {
        this.orderAsc = orderAsc;
    }

    public String getOrderColumn() {
        return this.orderColumn;
    }

    private void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public void setOrderInfo(String orderColumn, boolean orderAsc) {
        setOrderColumn(orderColumn);
        setOrderAsc(orderAsc);
    }

    public String toString() {
        return toKey();
    }

    public String toKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.orderColumn);
        sb.append("_");
        sb.append(this.orderAsc);
        sb.append("_");
        sb.append(this.limit);
        sb.append("_");
        sb.append(this.offset);
        return sb.toString();
    }
}

