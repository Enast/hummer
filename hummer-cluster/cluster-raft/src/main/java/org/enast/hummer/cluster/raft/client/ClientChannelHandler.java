package org.enast.hummer.cluster.raft.client;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

/**
 * @author zhujinming6
 * @create 2020-05-08 16:52
 * @update 2020-05-08 16:52
 **/
@Log4j2
public class ClientChannelHandler implements CompletionHandler<Void, ClientChannelHandler> {

    AsynchronousSocketChannel channel;

    public ClientChannelHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    /**
     * Invoked when an operation has completed.
     *
     * @param result     The result of the I/O operation.
     * @param attachment
     */
    @Override
    public void completed(Void result, ClientChannelHandler attachment) {
        log.info("客户端成功连接到服务器...");
    }

    /**
     * Invoked when an operation fails.
     *
     * @param exc        The exception to indicate why the I/O operation failed
     * @param attachment
     */
    @Override
    public void failed(Throwable exc, ClientChannelHandler attachment) {
        log.info("连接服务器失败...");
        log.error("", exc);
        try {
            channel.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
