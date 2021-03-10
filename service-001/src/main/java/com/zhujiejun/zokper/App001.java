package com.zhujiejun.zokper;

import com.zhujiejun.zokper.dist.lock.ZKDistLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import java.util.stream.IntStream;

@Slf4j
public class App001 {
    private final static long lockId = 20200730;

    private static void increment(ZKDistLock lock, long lockId) {
        try {
            lock.lock(lockId);
            byte[] data = lock.zookeeper.getData("/cnt", false, new Stat());
            int cnt = Integer.parseInt(new String(data)) + 1;
            lock.zookeeper.setData("/cnt", String.valueOf(cnt).getBytes(), -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(lockId);
        }
    }

    public static void main(String[] args) throws InterruptedException, KeeperException {
        /*new Thread(() -> {
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
        }).start();*/
        new ZKDistLock().zookeeper.setData("/cnt", "0".getBytes(), -1);
        IntStream.rangeClosed(1, 100).forEach(i -> new Thread(() -> increment(new ZKDistLock(), lockId), "thread-" + i).start());
    }
}
