package com.zhujiejun.zokper;

import com.zhujiejun.zokper.dist.lock.Lock;
import com.zhujiejun.zokper.dist.lock.ZKDistLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
public class App001 {
    public static void main(String[] args) throws InterruptedException {
        long lockId = 20200730;
        new Thread(() -> {
            Lock zkDistLock = new ZKDistLock();
            log.info("threadId-{}获取到分布式锁:{}", Thread.currentThread().getId(), zkDistLock.lock(lockId));
            try {
                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(5));
            } catch (InterruptedException e) {
                log.error("threadId-{}暂停时出现异常", Thread.currentThread().getId(), e);
            }
            zkDistLock.unlock(lockId);
        }).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            Lock zkDistLock = new ZKDistLock();
            log.info("threadId-{}获取到分布式锁:{}", Thread.currentThread().getId(), zkDistLock.lock(lockId));
            try {
                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(5));
            } catch (InterruptedException e) {
                log.error("threadId-{}暂停时出现异常", Thread.currentThread().getId(), e);
            }
            zkDistLock.unlock(lockId);
        }).start();
    }
}
