package org.tassemble.base.commons.dao.sql;

import java.util.List;

public abstract interface DBListHandler<T> {

    public abstract void handDBList(List<T> paramList);
}
