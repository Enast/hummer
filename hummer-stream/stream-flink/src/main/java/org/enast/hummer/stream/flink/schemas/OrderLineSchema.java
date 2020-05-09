package org.enast.hummer.stream.flink.schemas;

import com.google.gson.Gson;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.enast.hummer.stream.flink.model.OrderLineEvent;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * OrderLine Schema ，支持序列化和反序列化
 * <p>
 */
public class OrderLineSchema implements DeserializationSchema<OrderLineEvent>, SerializationSchema<OrderLineEvent> {

    private static final Gson gson = new Gson();

    @Override
    public OrderLineEvent deserialize(byte[] bytes) throws IOException {
        return gson.fromJson(new String(bytes), OrderLineEvent.class);
    }

    @Override
    public boolean isEndOfStream(OrderLineEvent orderLineEvent) {
        return false;
    }

    @Override
    public byte[] serialize(OrderLineEvent orderLineEvent) {
        return gson.toJson(orderLineEvent).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public TypeInformation<OrderLineEvent> getProducedType() {
        return TypeInformation.of(OrderLineEvent.class);
    }
}
