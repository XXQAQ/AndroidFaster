package com.xq.androidfaster.util;

public class FasterEventManager {

    private static Manager manager;

    protected static void regist(Object object,Object... objects) {
        manager.regist(object,objects);
    }

    protected static void unRegist(Object object,Object... objects) {
        manager.unRegist(object,objects);
    }

    protected static void send(Object message,Object... objects) {
        manager.send(message,objects);
    }

    protected static abstract class Manager {

        protected abstract void regist(Object object,Object... objects);

        protected abstract void unRegist(Object object,Object... objects);

        protected abstract void send(Object message,Object... objects);

    }

}
