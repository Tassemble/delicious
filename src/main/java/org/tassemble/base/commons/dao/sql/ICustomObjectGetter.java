package org.tassemble.base.commons.dao.sql;

import java.util.List;


public abstract interface ICustomObjectGetter<DomainObject> {

    public abstract List<DomainObject> getCustomList();
}