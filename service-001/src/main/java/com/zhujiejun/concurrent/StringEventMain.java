package com.zhujiejun.concurrent;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class StringEventMain {

    private static EventHandler<StringEvent> handle(String suffix) {
        return (event, sequence, endOfBatch) -> event.setValue(event.getValue().concat(suffix));
    }

    private static EventHandler<StringEvent> show(String msg) {
        return (event, sequence, endOfBatch) -> log.info("----------{} string is: [{}]----------", msg, event.getValue());
    }

    public static void main(String[] args) {
        int bufferSize = 1 << 10;
        Disruptor<StringEvent> disruptor = new Disruptor<>(StringEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith(show("init"))
                .then(handle("|"))
                .then(handle("-A"), handle("-B"), handle("-C"))
                .then(handle("|"))
                .then(handle("-D"), handle("-E"), handle("-F"))
                .then(show("processed"));
        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();

        disruptor.start();
        IntStream.rangeClosed(1, 1).forEach(i -> {
            String initString = RandomStringUtils.randomAlphabetic(10);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(initString));
            try {
                TimeUnit.MILLISECONDS.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
