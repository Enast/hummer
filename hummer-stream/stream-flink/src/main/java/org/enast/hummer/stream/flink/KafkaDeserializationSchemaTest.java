package org.enast.hummer.stream.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;
import org.enast.hummer.stream.flink.model.MetricEvent;
import org.enast.hummer.stream.flink.schemas.KafkaMetricSchema;
import org.enast.hummer.stream.flink.utils.ExecutionEnvUtil;
import org.enast.hummer.stream.flink.utils.GsonUtil;

import java.util.Properties;

import static org.enast.hummer.stream.flink.utils.KafkaConfigUtil.buildKafkaProps;


/**
 * Desc: KafkaDeserializationSchema
 */
@Slf4j
public class KafkaDeserializationSchemaTest {
    public static void main(String[] args) throws Exception {
        final ParameterTool parameterTool = ExecutionEnvUtil.createParameterTool(args);
        StreamExecutionEnvironment env = ExecutionEnvUtil.prepare(parameterTool);
        Properties props = buildKafkaProps(parameterTool);

        FlinkKafkaConsumer011<ObjectNode> kafkaConsumer = new FlinkKafkaConsumer011<>("test", new KafkaMetricSchema(true), props);

        env.addSource(kafkaConsumer).flatMap(new FlatMapFunction<ObjectNode, MetricEvent>() {
            @Override
            public void flatMap(ObjectNode jsonNodes, Collector<MetricEvent> collector) throws Exception {
                try {
                    //                            System.out.println(jsonNodes);
                    MetricEvent metricEvent = GsonUtil.fromJson(jsonNodes.get("value").asText(), MetricEvent.class);
                    collector.collect(metricEvent);
                } catch (Exception e) {
                    log.error("jsonNodes = {} convert to MetricEvent has an error", jsonNodes, e);
                }
            }
        }).print();
        env.execute();
    }
}
