package com.zhujiejun.zokper.dist.lock;

public interface Lock {
    boolean lock(Long lockId);

    void unlock(Long lockId);
}
