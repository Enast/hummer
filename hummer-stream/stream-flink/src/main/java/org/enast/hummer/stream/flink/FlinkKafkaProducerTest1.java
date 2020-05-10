package org.enast.hummer.stream.flink;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.util.Collector;
import org.enast.hummer.stream.flink.utils.ExecutionEnvUtil;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

/**
 * Desc: Flink 发送数据到 topic
 */
@Slf4j
public class FlinkKafkaProducerTest1 {

    public static final Random random = new Random();

    private static final Gson gson = new Gson();


    public static void main(String[] args) throws Exception {
        final ParameterTool parameterTool = ExecutionEnvUtil.createParameterTool(args);
        /** 获取{@link StreamExecutionEnvironment}的具体实现的实例 */
        StreamExecutionEnvironment env = ExecutionEnvUtil.prepare(parameterTool);
        env.setParallelism(1);
        env.addSource(new SourceFunction<Quota>() {
            @Override
            public void run(SourceContext<Quota> context) throws Exception {

                while (true) {
                    TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
                    Instant instant = Instant.ofEpochMilli(System.currentTimeMillis() + tz.getOffset(System.currentTimeMillis()));
                    String outline = String.format("{\"online\":1,\"resId\":\"1\"}", random.nextInt(10), random.nextInt(100), random.nextInt(1000), "pv", instant.toString());
                    HashMap<String, Object> message = gson.fromJson(outline, HashMap.class);
                    message.entrySet().forEach(m -> {
                        context.collect(new Quota(m.getKey(), m.getValue() + "",System.currentTimeMillis()));
                    });
                    Thread.sleep(200);
                    log.info(outline);
                }
            }

            @Override
            public void cancel() {

            }
        }).flatMap(new FlatMapFunction<Quota, String>() {
            @Override
            public void flatMap(Quota value, Collector<String> out) throws Exception {
                log.info("{}:{}", value.getKey(), value.getValue());
            }
        }).addSink(new FlinkKafkaProducer011<String>("localhost:9092", "user_behavior", new SimpleStringSchema())).name("flink-connectors-kafka");

        env.execute("flink kafka connector test");
    }
}

@Data
@AllArgsConstructor
class Quota {
    private String key;
    private String value;
    private Long time;
}
