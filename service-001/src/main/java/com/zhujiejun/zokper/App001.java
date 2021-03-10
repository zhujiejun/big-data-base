package com.zhujiejun.zokper;

import com.zhujiejun.comm.util.Formator;
import com.zhujiejun.zokper.dist.lock.Lock;
import com.zhujiejun.zokper.dist.lock.ZKDistLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class App001 {
    private final static String CNT_PATH = "/cnt";
    private final static String ORDER_PATH = "/order";

    private final static long cntLockId = 20200730;
    private final static long orderLockId = 20200731;

    private static void doJob() throws InterruptedException {
        new Thread(() -> {
            Lock zkDistLock = new ZKDistLock();
            log.info("threadId-{}获取到分布式锁:{}", Thread.currentThread().getId(), zkDistLock.lock(cntLockId));
            try {
                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(5));
            } catch (InterruptedException e) {
                log.error("threadId-{}暂停时出现异常", Thread.currentThread().getId(), e);
            }
            zkDistLock.unlock(cntLockId);
        }).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            Lock zkDistLock = new ZKDistLock();
            log.info("threadId-{}获取到分布式锁:{}", Thread.currentThread().getId(), zkDistLock.lock(cntLockId));
            try {
                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(5));
            } catch (InterruptedException e) {
                log.error("threadId-{}暂停时出现异常", Thread.currentThread().getId(), e);
            }
            zkDistLock.unlock(cntLockId);
        }).start();
    }

    private static void increment(ZKDistLock lock, long lockId) {
        try {
            lock.lock(lockId);
            byte[] data = lock.zookeeper.getData(CNT_PATH, false, new Stat());
            int cnt = Integer.parseInt(new String(data)) + 1;
            lock.zookeeper.setData(CNT_PATH, String.valueOf(cnt).getBytes(), -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(lockId);
        }
    }

    private static String getOrderNumber(ZKDistLock lock, long lockId) {
        int number = 0;
        try {
            lock.lock(lockId);
            byte[] data = lock.zookeeper.getData(ORDER_PATH, false, new Stat());
            number = Integer.parseInt(new String(data)) + 1;
            lock.zookeeper.setData(ORDER_PATH, String.valueOf(number).getBytes(), -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(lockId);
        }
        String serial = Formator.decimal(number, Formator.PATTERN001);
        String now = Formator.now(Formator.PATTERN002);
        return now.concat(serial);

    }

    public static void main(String[] args) throws InterruptedException, KeeperException {
        ZooKeeper zookeeper = new ZKDistLock().zookeeper;
        if (zookeeper.exists(ORDER_PATH, false) == null) {
            zookeeper.create(ORDER_PATH, StringUtils.EMPTY.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        zookeeper.setData(ORDER_PATH, "0".getBytes(), -1);
        //IntStream.rangeClosed(1, 100).forEach(i -> new Thread(() -> increment(new ZKDistLock(), cntLockId), "thread-" + i).start());
        IntStream.rangeClosed(1, 100).forEach(i -> new Thread(() -> {
            String orderNumber = getOrderNumber(new ZKDistLock(), orderLockId);
            log.info("=========the current order number is {}========", orderNumber);
        }, "thread-" + i).start());
    }
}
