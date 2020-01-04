package com.xq.androidfaster.util;

import java.lang.reflect.Type;
import java.util.List;

public class JsonConverter {

    private static Converter converter;

    public static <T>T jsonToObject(String json,Type type) throws RuntimeException {
        return converter.jsonToObject(json,type);
    }

    public static <T>List<T> jsonToListObject(String json,Type type) throws RuntimeException {
        return converter.jsonToListObject(json,type);
    }

    public static String objectToJson(Object object) {
        return converter.objectToJson(object);
    }

    public static void setConverter(Converter converter){
        JsonConverter.converter = converter;
    }

    public static abstract class Converter{

        public abstract <T>T jsonToObject(String json,Type type) throws RuntimeException;

        public abstract <T>List<T> jsonToListObject(String json,Type type) throws RuntimeException;

        public abstract String objectToJson(Object object);

    }

}
