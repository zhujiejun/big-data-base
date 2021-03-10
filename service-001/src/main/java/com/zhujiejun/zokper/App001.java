package com.zhujiejun.zokper;

import com.zhujiejun.zokper.dist.lock.Lock;
import com.zhujiejun.zokper.dist.lock.ZkDistLock;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class App001 {
    public static void main(String[] args) throws InterruptedException {
        long lockId = 20200730;

        new Thread(() -> {
            Lock zkDistLock = new ZkDistLock();
            System.out.println("ThreadId1=" + Thread.currentThread().getId());
            System.out.println("ThreadId=" + Thread.currentThread().getId() + "获取到分布式锁: " + zkDistLock.lock(lockId));
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error("ThreadId=" + Thread.currentThread().getId() + "暂停时出现异常", e);
            }
            zkDistLock.unlock(lockId);
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            Lock zkDistLock = new ZkDistLock();
            System.out.println("ThreadId2=" + Thread.currentThread().getId());
            System.out.println("ThreadId=" + Thread.currentThread().getId() + "获取到分布式锁: " + zkDistLock.lock(lockId));
        }).start();
    }
}
