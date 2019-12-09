package com.xq.androidfaster.util;

import java.util.List;

public class JsonConverter {

    private static Converter converter;

    public static <T>T jsonToObject(String json, Class<T> mClass, Object... objects) throws RuntimeException {
        return converter.jsonToObject(json,mClass,objects);
    }

    public static <T>List<T> jsonToListObject(String json, Class<T> mClass, Object... objects) throws RuntimeException {
        return converter.jsonToListObject(json,mClass,objects);
    }

    public static String objectToJson(Object object, Object... objects) {
        return converter.objectToJson(object,objects);
    }

    public static void setConverter(Converter converter){
        JsonConverter.converter = converter;
    }

    public static abstract class Converter{

        public abstract <T>T jsonToObject(String json,Class<T> mClass,Object... objects) throws RuntimeException;

        public abstract <T>List<T> jsonToListObject(String json,Class<T> mClass,Object... objects) throws RuntimeException;

        public abstract String objectToJson(Object object,Object... objects);

    }

}
