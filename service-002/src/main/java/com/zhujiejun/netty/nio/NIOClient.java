package com.zhujiejun.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws Exception {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器端的ip和port
        InetSocketAddress inetSocketAddress = new InetSocketAddress("192.168.100.100", 16666);
        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间,客户端不会阻塞,可以做其它工作..");
            }
        }
        //...如果连接成功,就发送数据
        String str = "hello, world!";
        //Wraps a byte array into a buffer
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //发送数据,将buffer数据写入channel
        socketChannel.write(buffer);

        System.out.println("----------");
        buffer.flip();
        buffer.clear();
        socketChannel.read(buffer);
        System.out.println("from服务端: " + new String(buffer.array()));
        System.in.read();
    }
}
