package org.enast.hummer.cluster.raft.client.impl;

import lombok.extern.log4j.Log4j2;
import org.enast.hummer.cluster.raft.client.AbstractSocketClient;
import org.enast.hummer.cluster.raft.client.ClientChannelHandler;
import org.enast.hummer.cluster.raft.client.ClientWriteHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Executors;

/**
 * @author zhujinming6
 * @create 2020-05-08 16:37
 * @update 2020-05-08 16:37
 **/
@Log4j2
public class AIOSocketClient extends AbstractSocketClient {

    AsynchronousSocketChannel channel;

    public AIOSocketClient(String address, int port) {
        super(address, port);
    }

    @Override
    public void connect() {
        try {
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withFixedThreadPool(2, Executors.defaultThreadFactory());
            channel = AsynchronousSocketChannel.open(group);
            channel.connect(new InetSocketAddress(address, port), null, new ClientChannelHandler(channel));
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public void sendMsg(String msg) {
        byte[] req = msg.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        //异步写
        channel.write(writeBuffer, writeBuffer, new ClientWriteHandler(channel));
    }

}
