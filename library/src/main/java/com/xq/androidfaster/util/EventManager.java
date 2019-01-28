package com.xq.androidfaster.util;

public class EventManager {

    private static Manager manager;

    public static void regist(Object object,Object... objects) {
        manager.regist(object,objects);
    }

    public static void unRegist(Object object,Object... objects) {
        manager.unRegist(object,objects);
    }

    public static void send(Object message,Object... objects) {
        manager.send(message,objects);
    }

    public static void cancle(Object message,Object... objects) {
        manager.cancle(message,objects);
    }

    public static void setManager(Manager manager){
        EventManager.manager = manager;
    }

    protected static abstract class Manager {

        public abstract void regist(Object object,Object... objects);

        public abstract void unRegist(Object object,Object... objects);

        public abstract void send(Object message,Object... objects);

        public abstract void cancle(Object message,Object... objects);

    }

}
