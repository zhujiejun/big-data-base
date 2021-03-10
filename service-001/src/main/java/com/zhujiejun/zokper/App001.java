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
            log.info("Thread1={}", Thread.currentThread().getId());
            log.info("current thread={}获取到分布式锁:{}", Thread.currentThread().getId(), zkDistLock.lock(lockId));
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error("Thread1={}暂停时出现异常", Thread.currentThread().getId() + "", e);
            }
            zkDistLock.unlock(lockId);
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            Lock zkDistLock = new ZkDistLock();
            log.info("Thread2={}", Thread.currentThread().getId());
            log.info("current thread={}获取到分布式锁:{}", Thread.currentThread().getId(), zkDistLock.lock(lockId));
        }).start();
    }
}
