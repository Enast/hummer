package org.enast.hummer.stream.flink;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.enast.hummer.stream.flink.model.MetricEvent;
import org.enast.hummer.stream.flink.schemas.MetricSchema;
import org.enast.hummer.stream.flink.utils.ExecutionEnvUtil;
import org.enast.hummer.stream.flink.utils.KafkaConfigUtil;

import java.time.Instant;
import java.util.HashMap;
import java.util.TimeZone;

/**
 */
@Slf4j
public class Main {
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws Exception {
        final ParameterTool parameterTool = ExecutionEnvUtil.createParameterTool(args);
        StreamExecutionEnvironment env = ExecutionEnvUtil.prepare(parameterTool);
        env.addSource(new SourceFunction<Quota>() {
            @Override
            public void run(SourceContext<Quota> context) throws Exception {
                while (true) {
                    String outline = "{\"online\":1,\"resId\":\"1\"}";
                    HashMap<String, Object> message = gson.fromJson(outline, HashMap.class);
                    message.entrySet().forEach(m -> {
                        context.collect(new Quota(m.getKey(), m.getValue() + ""));
                    });
                    Thread.sleep(1000);
                    log.info(outline);
                }
            }

            @Override
            public void cancel() {
            }
        }).print();

        //        DataStreamSource<MetricEvent> data = KafkaConfigUtil.buildSource(env);

        //        data.addSink(new FlinkKafkaProducer011<>(
        //                parameterTool.get("kafka.sink.brokers"),
        //                parameterTool.get("kafka.sink.topic"),
        //                new MetricSchema()
        //        )).name("flink-connectors-kafka")
        //                .setParallelism(parameterTool.getInt("stream.sink.parallelism"));

        env.execute("flink learning connectors kafka");
    }
}
