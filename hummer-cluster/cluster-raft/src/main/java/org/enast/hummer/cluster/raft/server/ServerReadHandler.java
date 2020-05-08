package org.enast.hummer.cluster.raft.server;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author zhujinming6
 * @create 2020-05-08 19:33
 * @update 2020-05-08 19:33
 **/
@Log4j2
public class ServerReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    // 用于读取半包消息和发送应答
    private AsynchronousSocketChannel channel;

    public ServerReadHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    /**
     * Invoked when an operation has completed.
     *
     * @param result     The result of the I/O operation.
     * @param attachment
     */
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] message = new byte[attachment.remaining()];
        attachment.get(message);
        try {
            String expression = new String(message, "UTF-8");
            log.info("服务器收到消息: {}", expression);
            doWrite(System.currentTimeMillis() + "");
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
    }

    private void doWrite(String result) {
        byte[] bytes = result.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        //异步写数据 参数与前面的read一样
        channel.write(writeBuffer, writeBuffer, new ServerWriteHandler(channel));
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
            this.channel.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
