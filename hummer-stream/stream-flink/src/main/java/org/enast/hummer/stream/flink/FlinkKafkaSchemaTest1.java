package org.enast.hummer.stream.flink;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaDeserializationSchemaWrapper;
import org.enast.hummer.stream.flink.model.MetricEvent;
import org.enast.hummer.stream.flink.schemas.CommonSchema;
import org.enast.hummer.stream.flink.schemas.MetricSchema;
import org.enast.hummer.stream.flink.utils.ExecutionEnvUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.enast.hummer.stream.flink.utils.KafkaConfigUtil.buildKafkaProps;

/**
 * Desc: Flink 消费 kafka topic，watermark 的修改
 */
public class FlinkKafkaSchemaTest1 {
    public static void main(String[] args) throws Exception {
        final ParameterTool parameterTool = ExecutionEnvUtil.createParameterTool(args);
        StreamExecutionEnvironment env = ExecutionEnvUtil.prepare(parameterTool);
        Properties props = buildKafkaProps(parameterTool);
        //kafka topic list
        List<String> topics = Arrays.asList(parameterTool.get("metrics.topic"));
        FlinkKafkaConsumer011<MetricEvent> consumer = new FlinkKafkaConsumer011<MetricEvent>(topics, new KafkaDeserializationSchemaWrapper<>(new CommonSchema<MetricEvent>(MetricEvent.class)), props);

        DataStreamSource<MetricEvent> data = env.addSource(consumer);

        data.print();

        env.execute("flink kafka connector test");
    }
}
