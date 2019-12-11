package com.xq.androidfaster.util;

import java.util.List;

public class JsonConverter {

    private static Converter converter;

    public static <T>T jsonToObject(String json, Class<T> mClass) throws RuntimeException {
        return converter.jsonToObject(json,mClass);
    }

    public static <T>List<T> jsonToListObject(String json, Class<T> mClass) throws RuntimeException {
        return converter.jsonToListObject(json,mClass);
    }

    public static String objectToJson(Object object) {
        return converter.objectToJson(object);
    }

    public static void setConverter(Converter converter){
        JsonConverter.converter = converter;
    }

    public static abstract class Converter{

        public abstract <T>T jsonToObject(String json,Class<T> mClass) throws RuntimeException;

        public abstract <T>List<T> jsonToListObject(String json,Class<T> mClass) throws RuntimeException;

        public abstract String objectToJson(Object object);

    }

}
