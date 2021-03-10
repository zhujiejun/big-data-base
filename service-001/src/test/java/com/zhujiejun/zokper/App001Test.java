package com.zhujiejun.zokper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App001Test {
    private ZooKeeper zooKeeper;
    private static final int sessionTimeout = 2000;
    private static final String connectString = "node101:2181,node102:2181,node103:2181";

    @Before
    public void test01() throws IOException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, event -> System.out.println(event.getState().toString()));
    }

    @Test
    public void test02() throws KeeperException, InterruptedException {
//        String node = zooKeeper.create("/temp", "zhujiejun".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        String node = zooKeeper.create("/atguigu", "zhujiejun".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println(node);
//        zooKeeper.delete("/temp", 0);
        zooKeeper.delete("/atguigu", 0);
    }

    @Test
    public void test03() throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.setData("/atguigu", "pangyao".getBytes(), 2);
        System.out.println(stat.getVersion());
    }

    @Test
    public void test04() throws KeeperException, InterruptedException {
        /*Stat stat = new Stat();
        stat.setVersion(0);
        byte[] data = zooKeeper.getData("/atguigu", false, stat);
        System.out.println(new String(data));*/

        List<String> strings = zooKeeper.getChildren("/", true);
        strings.forEach(System.out::println);

        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
