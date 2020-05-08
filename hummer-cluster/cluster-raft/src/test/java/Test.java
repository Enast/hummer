import org.enast.hummer.cluster.raft.client.impl.AIOSocketClient;
import org.enast.hummer.cluster.raft.server.impl.AIOSocketServer;

/**
 * @author zhujinming6
 * @create 2020-05-08 20:04
 * @update 2020-05-08 20:04
 **/
public class Test {

    @org.junit.Test
    public void test() throws InterruptedException {
        AIOSocketServer socketServer = new AIOSocketServer(8009);
        socketServer.open();
        AIOSocketClient socketClient = new AIOSocketClient("127.0.0.1", 8009);
        socketClient.connect();
        while (true) {
            socketClient.sendMsg("123");
            Thread.sleep(10 * 100);
        }
    }
}
