package com.zhujiejun.zokper.dist.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZkDistLock implements Lock {
    //Zookeeper客户端
    private ZooKeeper zookeeper;

    //创建分布式锁的过程中,开始和等待请求创建分布式锁的信号标志
    private CountDownLatch creatingSemaphore;

    //与Zookeeper成功建立连接的信号标志
    private final CountDownLatch connectedSemaphore = new CountDownLatch(1);

    //分布式锁的过期时间 单位:毫秒
    private static final Long DISTRIBUTED_KEY_OVERDUE_TIME = 30000L;

    private static final int sessionTimeout = 5000;
    private static final String connectString = "node101:2181,node101:2181,node102:2181,node103:2181";

    public ZkDistLock() {
        try {
            this.zookeeper = new ZooKeeper(connectString, sessionTimeout, new ZookeeperWatcher());
            try {
                connectedSemaphore.await();
            } catch (InterruptedException ite) {
                log.error("等待Zookeeper成功建立连接的过程中,线程抛出异常", ite);
            }
            log.info("与Zookeeper成功建立连接");
        } catch (Exception e) {
            log.error("与Zookeeper建立连接时出现异常", e);
        }
    }

    //获取分布式锁
    @Override
    public boolean lock(Long lockId) {
        String path = "/product-lock-" + lockId;
        try {
            zookeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            log.info("ThreadId=" + Thread.currentThread().getId() + "创建临时节点成功");
            return true;
        } catch (Exception e) {
            // 若临时节点已存在,则会抛出异常: NodeExistsException        
            while (true) {
                // 相当于给znode注册了一个监听器,查看监听器是否存在            
                try {
                    Stat stat = zookeeper.exists(path, true);
                    if (stat != null) {
                        this.creatingSemaphore = new CountDownLatch(1);
                        this.creatingSemaphore.await(DISTRIBUTED_KEY_OVERDUE_TIME, TimeUnit.MILLISECONDS);
                        this.creatingSemaphore = null;
                    }
                    zookeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                    return true;
                } catch (Exception ex) {
                    log.error("ThreadId=" + Thread.currentThread().getId() + ",查看临时节点时出现异常", ex);
                }
            }
        }
    }

    //释放分布式锁
    @Override
    public void unlock(Long lockId) {
        String path = "/product-lock-" + lockId;
        try {
            // 第二个参数version是数据版本 每次znode内数据发生变化,都会使version自增,
            // 但由于分布式锁创建的临时znode没有存数据,因此version=-1        
            zookeeper.delete(path, -1);
            log.info("成功释放分布式锁, lockId=" + lockId + ", ThreadId=" + Thread.currentThread().getId());
        } catch (Exception e) {
            log.error("释放分布式锁失败,lockId=" + lockId, e);
        }
    }

    private class ZookeeperWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            log.info("接收到事件: " + event.getState() + ", ThreadId=" + Thread.currentThread().getId());
            if (Event.KeeperState.SyncConnected == event.getState()) {
                connectedSemaphore.countDown();
            }
            if (creatingSemaphore != null) {
                creatingSemaphore.countDown();
            }
        }
    }
}
