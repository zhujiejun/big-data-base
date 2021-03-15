package com.zhujiejun.concurrent;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

public class LongEventMain {
    public static void main(String[] args) throws Exception {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // Connect the producer
        LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);

        // Connect the handler
        //disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Handle event: " + event));
        LongEventHandler handler = new LongEventHandler();
        disruptor.handleEventsWith(handler);

        // Start the Disruptor, starts all threads running
        disruptor.start();

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            byteBuffer.putLong(0, l);
            //ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(buffer.getLong(0)), byteBuffer);
            producer.onData(byteBuffer);
            Thread.sleep(1000);
        }
    }
}
