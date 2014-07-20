package org.tassemble.base.commons.dao;

import java.util.List;

public abstract interface CommonObjectDao<DomainObject> {

    public abstract int add(DomainObject paramDomainObject);

    public abstract int add(List<DomainObject> paramList);

    public abstract boolean delete(DomainObject paramDomainObject);

    public abstract List<DomainObject> getAll();

    public abstract List<DomainObject> getAll(ListCondition paramListCondition);

    /** @deprecated */
    public abstract void deleteAll();

    public abstract long getCount();

    /** @deprecated */
    public abstract boolean update(DomainObject paramDomainObject);
}
