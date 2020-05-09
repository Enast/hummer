package org.enast.hummer.stream.flink.schemas;

import com.google.gson.Gson;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.enast.hummer.stream.flink.model.ShopEvent;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Shop Schema ，支持序列化和反序列化
 * <p>
 */
public class ShopSchema implements DeserializationSchema<ShopEvent>, SerializationSchema<ShopEvent> {

    private static final Gson gson = new Gson();

    @Override
    public ShopEvent deserialize(byte[] bytes) throws IOException {
        return gson.fromJson(new String(bytes), ShopEvent.class);
    }

    @Override
    public boolean isEndOfStream(ShopEvent shopEvent) {
        return false;
    }

    @Override
    public byte[] serialize(ShopEvent shopEvent) {
        return gson.toJson(shopEvent).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public TypeInformation<ShopEvent> getProducedType() {
        return TypeInformation.of(ShopEvent.class);
    }
}
