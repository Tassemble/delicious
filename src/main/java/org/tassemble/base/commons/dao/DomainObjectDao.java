package org.tassemble.base.commons.dao;

import java.util.List;



public abstract interface DomainObjectDao<DomainObject> extends CommonObjectDao<DomainObject> {

    public abstract boolean deleteById(long paramLong);

    public abstract List<DomainObject> getAllByIds(Long[] paramArrayOfLong, boolean paramBoolean);

    public abstract List<DomainObject> getAllByIds(Long[] paramArrayOfLong);

    public abstract DomainObject getById(long paramLong);
}
