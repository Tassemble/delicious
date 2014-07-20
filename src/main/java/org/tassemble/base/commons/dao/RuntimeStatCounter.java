package org.tassemble.base.commons.dao;




public abstract interface RuntimeStatCounter {

    public abstract void incrDoneCount();

    public abstract void incrFailCount();

    public abstract String getCountDone();

    public abstract String getCountFail();
}