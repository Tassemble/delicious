package org.tassemble.base.commons.domain;


public abstract class ObjectBase {

    private long   modifyTime;
    private String modifyIP;
    private long   createTime;

    public ObjectBase() {
        this.modifyIP = "";
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long cretateTime) {
        this.createTime = cretateTime;
    }

    public String getModifyIP() {
        return this.modifyIP;
    }

    public void setModifyIP(String modifyIP) {
        this.modifyIP = modifyIP;
    }

    public long getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
}
