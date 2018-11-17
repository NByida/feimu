package com.tvsonar.android.feimu.net.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.tvsonar.android.feimu.common.RegUtils;

import java.lang.reflect.Type;

public class BooleanDeserialozer implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String str = json.getAsString();
        //如果是数字，‘0’代表false，其他数字代表true，如果不是数字，"true"代表true,其他代表false
        try {
            if (RegUtils.isNumber(str)) {
                return !"0".equals(str);
            } else if ("true".equals(str)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}

