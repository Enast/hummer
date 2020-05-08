package org.enast.hummer.cluster.raft.client;

/**
 * @author zhujinming6
 * @create 2020-05-08 16:24
 * @update 2020-05-08 16:24
 **/
public abstract class AbstractSocketClient implements SocketClient {

    protected String address;
    protected Integer port;

    public AbstractSocketClient(String address, int port) {
        this.address = address;
        this.port = port;
    }


}
