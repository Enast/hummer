package org.enast.hummer.cluster.raft.server;

/**
 * @author zhujinming6
 * @create 2020-05-08 16:25
 * @update 2020-05-08 16:25
 **/
public abstract class AbstractSocketServer implements SocketServer {

    protected Integer port;

    public AbstractSocketServer(Integer port) {
        this.port = port;
    }
}
