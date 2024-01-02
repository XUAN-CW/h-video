package com.xuanchengwei.hvideoapp.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.xuanchengwei.baizizhan.constaint.Constants;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
* Created by tao.zeng on 2020/6/23.
* <p>
* 处理LocalDateTime序列化与反序列化
*/
public final class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.format(Constants.INSTANCE.getDateTimeFormatter()));
    }

//     转时间戳
//    ZoneId zone = ZoneId.systemDefault();
//    long timestamp = ldt.atZone(zone).toInstant().toEpochMilli();

    @Override
    public LocalDateTime deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        String timestamp = element.getAsJsonPrimitive().getAsString();
        return LocalDateTime.parse(timestamp, Constants.INSTANCE.getDateTimeFormatter());
    }
}
