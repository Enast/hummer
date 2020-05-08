package org.enast.hummer.cluster.raft.server.impl;

import lombok.extern.log4j.Log4j2;
import org.enast.hummer.cluster.raft.AIOSession;
import org.enast.hummer.cluster.raft.server.AbstractSocketServer;
import org.enast.hummer.cluster.raft.server.ServerCompletionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.List;

/**
 * @author zhujinming6
 * @create 2020-05-08 16:37
 * @update 2020-05-08 16:37
 **/
@Log4j2
public class AIOSocketServer extends AbstractSocketServer {

    public static List<AIOSession> sessionList = new ArrayList<>();

    public AIOSocketServer(Integer port) {
        super(port);
    }

    @Override
    public void open() {
        try {
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withFixedThreadPool(2, Executors.defaultThreadFactory());
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group).bind(new InetSocketAddress(port));
            log.info("服务器已启动，端口号：{}" + port);
            server.accept(null, new ServerCompletionHandler(server));
            //            server.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
