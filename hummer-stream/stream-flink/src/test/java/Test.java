import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author zhujinming6
 * @create 2020-05-09 10:27
 * @update 2020-05-09 10:27
 **/
public class Test {

    @org.junit.Test
    public void test() {
    }

    @org.junit.Test
    public void mqTest() throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.execute("flink learning project template");
    }
}
