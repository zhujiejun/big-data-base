package com.zhujiejun.concurrent;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class StringEventMain {

    private static EventHandler<StringEvent> show(String msg) {
        return (event, sequence, endOfBatch) ->
                log.info("----------{} string is: [{}]----------", msg, event.getValue());
    }

    private static EventHandler<StringEvent> delimit(String delimiter) {
        return (event, sequence, endOfBatch) -> event.setValue(event.getValue().concat(delimiter));
    }

    private static EventHandler<StringEvent> handle(String suffix) {

        return (event, sequence, endOfBatch) -> {
            try {
                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(1, 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            event.setValue(event.getValue().concat(suffix));
        };
    }

    public static void main(String[] args) {
        int bufferSize = 1 << 10;
        Disruptor<StringEvent> disruptor = new Disruptor<>(StringEvent::new, bufferSize,
                DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new BlockingWaitStrategy());

        disruptor.handleEventsWith(show("initiated"))
                .then(delimit("|"))
                .then(handle("-A"), handle("-B"), handle("-C"))
                .then(delimit("|"))
                .then(handle("-D"), handle("-E"), handle("-F"))
                .then(show("processed"));
        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();

        disruptor.start();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            String initString = RandomStringUtils.randomAlphabetic(1 << 4);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(initString));
            try {
                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(3, 5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
