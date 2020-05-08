package org.enast.hummer.cluster.raft.client;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author zhujinming6
 * @create 2020-05-08 19:48
 * @update 2020-05-08 19:48
 **/
@Log4j2
public class ClientReadHandler implements CompletionHandler<Integer, ByteBuffer> {


    private AsynchronousSocketChannel clientChannel;

    public ClientReadHandler(AsynchronousSocketChannel clientChannel) {
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
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        String body;
        try {
            body = new String(bytes, "UTF-8");
            log.info("客户端收到结果:" + body);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
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
        log.info("数据读取失败...");
        try {
            clientChannel.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
