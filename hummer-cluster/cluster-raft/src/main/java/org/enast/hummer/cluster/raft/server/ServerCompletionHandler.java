package org.enast.hummer.cluster.raft.server;

import lombok.extern.log4j.Log4j2;
import org.enast.hummer.cluster.raft.AIOSession;
import org.enast.hummer.cluster.raft.server.impl.AIOSocketServer;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author zhujinming6
 * @create 2020-05-08 18:56
 * @update 2020-05-08 18:56
 **/
@Log4j2
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {

    AsynchronousServerSocketChannel channel;

    public ServerCompletionHandler(AsynchronousServerSocketChannel channel) {
        this.channel = channel;
    }

    /**
     * Invoked when an operation has completed.
     *
     * @param result     The result of the I/O operation.
     * @param attachment
     */
    @Override
    public void completed(AsynchronousSocketChannel result, Object attachment) {
        // 接收到新的客户端连接时调用，result就是和客户端的连接对话，此时可以通过result和客户端进行通信
        //创建新的Buffer
        log.info("accept completed");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //异步读  第三个参数为接收消息回调的业务Handler
        result.read(buffer, buffer, new ServerReadHandler(result));
        AIOSession session = new AIOSession(result);
        // 新建回话，并保存
        AIOSocketServer.sessionList.add(session);
        // 继续监听accept
        channel.accept(null, this);
    }

    /**
     * Invoked when an operation fails.
     *
     * @param exc        The exception to indicate why the I/O operation failed
     * @param attachment
     */
    @Override
    public void failed(Throwable exc, Object attachment) {
        log.info("accept failed");
    }
}
