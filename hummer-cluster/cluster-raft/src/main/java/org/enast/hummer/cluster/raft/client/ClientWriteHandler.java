package org.enast.hummer.cluster.raft.client;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author zhujinming6
 * @create 2020-05-08 19:44
 * @update 2020-05-08 19:44
 **/
@Log4j2
public class ClientWriteHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel clientChannel;

    public ClientWriteHandler(AsynchronousSocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    /**
     * Invoked when an operation has completed.
     *
     * @param result The result of the I/O operation.
     * @param buffer
     */
    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        //完成全部数据的写入
        if (buffer.hasRemaining()) {
            clientChannel.write(buffer, buffer, this);
        } else {
            //读取数据
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            clientChannel.read(readBuffer, readBuffer, new ClientReadHandler(clientChannel));
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
        log.info("数据发送失败...");
        try {
            clientChannel.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
