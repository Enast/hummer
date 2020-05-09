package org.enast.hummer.stream.flink.schemas;

import com.google.gson.Gson;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author zhujinming6
 * @create 2020-05-09 11:37
 * @update 2020-05-09 11:37
 **/
public class CommonSchema<E> implements DeserializationSchema<E>, SerializationSchema<E> {

    Class aClass;

    public CommonSchema(Class aClass) {
        this.aClass = aClass;
    }

    private static final Gson gson = new Gson();

    @Override
    public E deserialize(byte[] bytes) throws IOException {
        return gson.fromJson(new String(bytes), (Class<E>) aClass);
    }

    @Override
    public boolean isEndOfStream(E e) {
        return false;
    }

    @Override
    public byte[] serialize(E e) {
        return gson.toJson(e).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public TypeInformation<E> getProducedType() {
        return TypeInformation.of(aClass);
    }
}
