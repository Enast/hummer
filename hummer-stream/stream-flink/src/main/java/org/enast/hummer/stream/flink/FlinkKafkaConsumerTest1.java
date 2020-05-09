package org.enast.hummer.stream.flink;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.enast.hummer.stream.flink.model.MetricEvent;
import org.enast.hummer.stream.flink.schemas.CommonSchema;
import org.enast.hummer.stream.flink.utils.ExecutionEnvUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.enast.hummer.stream.flink.utils.KafkaConfigUtil.buildKafkaProps;


public class FlinkKafkaConsumerTest1 {
    public static void main(String[] args) throws Exception {
        final ParameterTool parameterTool = ExecutionEnvUtil.createParameterTool(args);
        StreamExecutionEnvironment env = ExecutionEnvUtil.prepare(parameterTool);
        Properties props = buildKafkaProps(parameterTool);
        //kafka topic list
        List<String> topics = Arrays.asList(parameterTool.get("metrics.topic"), parameterTool.get("logs.topic"));
        FlinkKafkaConsumer011<MetricEvent> consumer = new FlinkKafkaConsumer011<>(topics, new CommonSchema<MetricEvent>(MetricEvent.class), props);
        //kafka topic Pattern
        //FlinkKafkaConsumer011<MetricEvent> consumer = new FlinkKafkaConsumer011<>(java.utils.regex.Pattern.compile("test-topic-[0-9]"), new MetricSchema(), props);


        //        consumer.setStartFromLatest();
        //        consumer.setStartFromEarliest()
        DataStreamSource<MetricEvent> data = env.addSource(consumer);

        data.print();

        env.execute("flink kafka connector test");
    }
}
