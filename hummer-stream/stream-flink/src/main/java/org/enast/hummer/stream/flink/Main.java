package org.enast.hummer.stream.flink;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.enast.hummer.stream.flink.utils.ExecutionEnvUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
@Slf4j
public class Main {
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws Exception {
        final ParameterTool parameterTool = ExecutionEnvUtil.createParameterTool(args);
        StreamExecutionEnvironment env = ExecutionEnvUtil.prepare(parameterTool);
//        env.setParallelism(1); // 默认是5
        TumblingEventTimeWindows timeWindows = TumblingEventTimeWindows.of(Time.seconds(10));
        env.addSource(new SourceFunction<Tuple2<String, Quota>>() {
            @Override
            public void run(SourceContext<Tuple2<String, Quota>> context) throws Exception {
                while (true) {
                    String outline = "{\"workStatus\":1,\"onlineStatus\":1,\"resId\":\"1\"}";
                    String outline2 = "{\"workStatus\":1,\"onlineStatus\":1,\"resId\":\"2\"}";
                    String outline3 = "{\"workStatus\":1,\"onlineStatus\":1,\"resId\":\"3\"}";
                    HashMap<String, Object> message = gson.fromJson(outline, HashMap.class);
                    message.entrySet().forEach(m -> {
                        Double i = Double.valueOf(Math.random() * 2);
                        context.collect(new Tuple2<String, Quota>((String) message.get("resId"), new Quota(m.getKey(), i.intValue() + "", System.currentTimeMillis())));
                    });

                    HashMap<String, Object> message2 = gson.fromJson(outline2, HashMap.class);
                    message2.entrySet().forEach(m -> {
                        Double i = Double.valueOf(Math.random() * 2);
                        context.collect(new Tuple2<String, Quota>((String) message2.get("resId"), new Quota(m.getKey(), i.intValue() + "", System.currentTimeMillis())));
                    });

                    HashMap<String, Object> message3 = gson.fromJson(outline3, HashMap.class);
                    message3.entrySet().forEach(m -> {
                        Double i = Double.valueOf(Math.random() * 2);
                        context.collect(new Tuple2<String, Quota>((String) message3.get("resId"), new Quota(m.getKey(), i.intValue() + "", System.currentTimeMillis())));
                    });

                    Thread.sleep(1000);
//                    log.info(outline);
                }
            }

            @Override
            public void cancel() {
            }
        }).filter((tuple2) -> {
            if (tuple2.f1.getKey().equals("resId")) {
                return false;
            }
            return true;
        }).assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Tuple2<String, Quota>>() {
            public long currentMaxTimestamp = 0L;
            public static final long maxOutOfOrderness = 10000L;//最大允许的乱序时间是10s

            @Override
            public long extractTimestamp(Tuple2<String, Quota> element, long previousElementTimestamp) {
                return currentMaxTimestamp = Math.max(element.f1.getTime(), currentMaxTimestamp);
            }

            @Nullable
            @Override
            public Watermark getCurrentWatermark() {
                return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
            }
        }).keyBy(0).window(timeWindows).apply(new WindowFunction<Tuple2<String, Quota>, Tuple3<String, Quota, String>, Tuple, TimeWindow>() {
            @Override
            public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple2<String, Quota>> input, Collector<Tuple3<String, Quota, String>> out) throws Exception {
                System.out.println("maxTimestamp>"+window.maxTimestamp());
                AtomicInteger count = new AtomicInteger();
                int targetCount = 5;
                Iterator iterator = input.iterator();
                Tuple2<String, Quota> st1 = null;
                while (iterator.hasNext()) {
                    Tuple2<String, Quota> st = (Tuple2<String, Quota>) iterator.next();
                    if (Double.valueOf(st.f1.getValue()).intValue() == 1 && st.f1.getKey().equals("onlineStatus")) {
                        count.getAndIncrement();
                    } else {
                        continue;
                    }
                    if (st1 == null) {
                        st1 = st;
                    }
                    if (count.get() > targetCount) {
                        out.collect(new Tuple3<>(st.f0, st.f1, "资源：" + st.f0 + "的指标（" + st.f1.getKey() + ")10s内等于1出现" + targetCount + "次，满足条件"));
                        break;
                    }
                }
//                input.forEach(st->{
//                    System.out.println(">>> "+st.f0+","+st.f1.getKey()+","+st.f1.getValue());
//                    return;
//                });
                System.out.println(Thread.currentThread().getName()+">>> " + st1.f0 + "," + st1.f1.getKey() + "," + st1.f1.getValue());
            }
        }).print("stream result");
        env.executeAsync("flink alarm");

        Thread.sleep(30*1000);
        // 如何修改时间窗口的时间
        System.out.println("1111");

    }
}
