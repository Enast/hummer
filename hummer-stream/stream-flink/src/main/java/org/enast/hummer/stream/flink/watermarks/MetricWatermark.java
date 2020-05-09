package org.enast.hummer.stream.flink.watermarks;

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.enast.hummer.stream.flink.model.MetricEvent;

import javax.annotation.Nullable;

public class MetricWatermark implements AssignerWithPeriodicWatermarks<MetricEvent> {

    private long currentTimestamp = Long.MIN_VALUE;

    @Override
    public long extractTimestamp(MetricEvent metricEvent, long previousElementTimestamp) {
        long timestamp = metricEvent.getTimestamp();
        currentTimestamp = Math.max(timestamp, currentTimestamp);
        return timestamp;
    }

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        long maxTimeLag = 5000;
        return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - maxTimeLag);

    }
}
