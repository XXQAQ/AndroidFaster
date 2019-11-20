package com.xq.androidfaster.util;

import com.xq.androidfaster.util.tools.ThreadUtils;

public class Polling {

    private Runnable run;

    private CustomThread thread;

    private boolean isOnUiThread = true;
    private int millis;
    private int delay;

    public Polling(int millis, Runnable run) {
        this(millis,0,true,run);
    }

    public Polling(int millis, int delay, boolean isOnUiThread, Runnable run) {
        this.millis = millis;
        this.delay = delay;
        this.isOnUiThread = isOnUiThread;
        this.run = run;
    }

    public void start(){
        if (thread != null && thread.isAlive())
            destory();

        thread = new CustomThread();
        thread.start();
    }

    public void resume(){
        if (thread != null)
            thread.resumeThread();
    }

    public void pause(){
        if (thread != null)
            thread.pauseThread();
    }

    public void destory(){
        if (thread != null)
            thread.interrupt();
    }

    public boolean isStart(){
        if (thread != null){
            return thread.isStart();
        }
        return false;
    }

    public boolean isPause(){
        if (thread != null){
            return thread.isPause();
        }
        return false;
    }

    public boolean isPolling(){
        return thread != null && !thread.isInterrupted() && !isPause();
    }

    public class CustomThread extends Thread {

        private final Object lock = new Object();

        private boolean isFirstRun = true;

        private boolean isStart = false;

        private boolean isPause = false;

        /**
         * 调用该方法实现线程的暂停
         */
        public void pauseThread(){
            isPause = true;
        }

        /**
         * 调用该方法实现恢复线程的运行
         */
        public void resumeThread(){
            isPause = false;
            synchronized (lock){
                lock.notify();
            }
        }

        /**
         * 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
         */
        private void onPause() throws InterruptedException {
            synchronized (lock) {
                lock.wait();
            }
        }

        @Override
        public void run() {
            super.run();

            isStart = true;

            while(true){
                try {

                    while (isPause){
                        onPause();
                    }

                    Thread.sleep(isFirstRun?delay : millis);

                    isFirstRun = false;

                    if (isOnUiThread)
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                run.run();
                            }
                        });
                    else
                        run.run();
                }catch (Exception e){
                    e.printStackTrace();
                    isPause = true;
                    break;
                }
            }
        }

        public boolean isPause() {
            return isPause;
        }

        public boolean isStart() {
            return isStart;
        }
    }

}
