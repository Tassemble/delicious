package org.tassemble.base.commons.dao.sql;

import java.util.concurrent.atomic.AtomicLong;

import org.tassemble.base.commons.dao.RuntimeStatCounter;

public class RuntimeStatCounterImpl implements RuntimeStatCounter {

    private AtomicLong doneCount;
    private AtomicLong failCount;

    public RuntimeStatCounterImpl() {
        this.doneCount = new AtomicLong();

        this.failCount = new AtomicLong();
    }

    public String getCountDone() {
        long cur = System.currentTimeMillis();
        return String.format("{%1$d,%2$d}", new Object[] { Long.valueOf(cur), Long.valueOf(this.doneCount.get()) });
    }

    public String getCountFail() {
        long cur = System.currentTimeMillis();
        return String.format("{%1$d,%2$d}", new Object[] { Long.valueOf(cur), Long.valueOf(this.failCount.get()) });
    }

    public void incrDoneCount() {
        this.doneCount.incrementAndGet();
    }

    public void incrFailCount() {
        this.failCount.incrementAndGet();
    }
}

