package org.tassemble.base.commons.dao.sql;

import org.tassemble.base.commons.dto.ObjectConfig;

public abstract interface ObjectConfigInside {

    public abstract void setObjectConfig(ObjectConfig paramObjectConfig);

    public abstract ObjectConfig getObjectConfig();
}
