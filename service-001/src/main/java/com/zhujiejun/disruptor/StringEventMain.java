package com.zhujiejun.disruptor;

import com.google.common.base.Stopwatch;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class StringEventMain {
    private static final Executor executor = Executors.newCachedThreadPool();

    private static EventHandler<StringEvent> show(String msg) {
        return (event, sequence, endOfBatch) -> {
            log.info("==========1.current thread is: {}==========", Thread.currentThread().toString());
            log.info("=========={} string is: [{}]============{}", msg, event.getValue(), StringUtils.equals(msg, "processed") ? "\n" : "");
        };
    }

    private static EventHandler<StringEvent> delimit(String delimiter) {
        return (event, sequence, endOfBatch) -> {
            log.info("==========2.current thread is: {}==========", Thread.currentThread().toString());
            event.setValue(event.getValue().concat(delimiter));
        };
    }

    private static EventHandler<StringEvent> append(String suffix) {
        return (event, sequence, endOfBatch) -> {
            log.info("==========3.current thread is: {}==========", Thread.currentThread().toString());
            /*try {
                TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(1 << 7, 1 << 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            event.setValue(event.getValue().concat(suffix));
        };
    }

    public static void main(String[] args) {
        int bufferSize = 1 << 20;
        Stopwatch watch = Stopwatch.createStarted();

        Disruptor<StringEvent> disruptor = new Disruptor<>(StringEvent::new, bufferSize,
                DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new YieldingWaitStrategy());

        disruptor.handleEventsWith(show("initiated"))
                .then(delimit("|"))
                .then(append("-A"), append("-B"))
                .then(delimit("|"))
                .then(append("-C"), append("-D"))
                .then(delimit("|"))
                .then(append("-E"), append("-F"))
                .then(show("processed"));

        disruptor.start();

        IntStream.rangeClosed(1, 2).forEach(i -> {
            String initString = RandomStringUtils.randomAlphabetic(1 << 5);
            disruptor.publishEvent((event, sequence) -> event.setValue(initString));
            /*try {
                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(1, 5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        });

        disruptor.shutdown();
        log.warn("==========total time consumption is: {} ms.==========", watch.elapsed(TimeUnit.MILLISECONDS));
    }
}
