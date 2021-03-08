package com.zhujiejun.concurrent;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@SuppressWarnings("all")
public class StringEventMain {

    private static final Executor executor = Executors.newFixedThreadPool(3);

    private static EventHandler<StringEvent> show(String msg) {
        return (event, sequence, endOfBatch) ->
                log.info("=========={} string is: [{}]============{}",
                        msg, event.getValue(),
                        StringUtils.equals(msg, "processed") ? "\n" : "");
    }

    private static EventHandler<StringEvent> delimit(String delimiter) {
        return (event, sequence, endOfBatch) -> event.setValue(event.getValue().concat(delimiter));
    }

    private static EventHandler<StringEvent> handle(String suffix) {

        return (event, sequence, endOfBatch) -> {
            try {
                TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(100, 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            event.setValue(event.getValue().concat(suffix));
        };
    }

    public static void main(String[] args) {
        int bufferSize = 1 << 12;
        Disruptor<StringEvent> disruptor = new Disruptor<>(StringEvent::new, bufferSize,
                DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new YieldingWaitStrategy());

        disruptor.handleEventsWith(show("initiated"))
                .then(delimit("|"))
                .then(handle("-A"), handle("-B"))
                .then(delimit("|"))
                .then(handle("-C"), handle("-D"))
                .then(delimit("|"))
                .then(handle("-E"), handle("-F"))
                .then(show("processed"));

        disruptor.start();
        //RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();
        IntStream.rangeClosed(1, 5).forEach(i -> {
            String initString = RandomStringUtils.randomAlphabetic(1 << 4);
            //ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(initString));
            disruptor.publishEvent((event, sequence) -> event.setValue(initString));
            try {
                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(1, 5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            TimeUnit.MILLISECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
