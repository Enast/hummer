package org.enast.hummer.cluster.raft.server;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author zhujinming6
 * @create 2020-05-08 19:54
 * @update 2020-05-08 19:54
 **/
@Log4j2
public class ServerWriteHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ServerWriteHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    /**
     * Invoked when an operation has completed.
     *
     * @param result The result of the I/O operation.
     * @param buffer
     */
    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        // 如果没有发送完，就继续发送直到完成
        if (buffer.hasRemaining()) {
            channel.write(buffer, buffer, this);
        } else {
            // 创建新的Buffer
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            // 异步读 第三个参数为接收消息回调的业务Handler
            channel.read(readBuffer, readBuffer, new ServerReadHandler(channel));
        }
    }

    /**
     * Invoked when an operation fails.
     *
     * @param exc        The exception to indicate why the I/O operation failed
     * @param attachment
     */
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
