package com.xq.androidfaster.util;

public class EventManager {

    private static Manager manager;

    public static void regist(Object object) {
        manager.regist(object);
    }

    public static void unRegist(Object object) {
        manager.unRegist(object);
    }

    public static void send(Object message) {
        manager.send(message);
    }

    public static void cancel(Object message) {
        manager.cancel(message);
    }

    public static void setManager(Manager manager){
        EventManager.manager = manager;
    }

    public static abstract class Manager {

        public abstract void regist(Object object);

        public abstract void unRegist(Object object);

        public abstract void send(Object message);

        public abstract void cancel(Object message);

    }

}
