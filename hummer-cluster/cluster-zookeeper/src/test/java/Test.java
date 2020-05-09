import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * @author zhujinming6
 * @create 2020-05-09 9:26
 * @update 2020-05-09 9:26
 **/
public class Test {


    @org.junit.Test
    public void test() {
        // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryNTimes(10, 5000));
        client.start();
    }
}
