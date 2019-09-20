package com.xq.androidfaster.util;

import com.xq.androidfaster.util.tools.ThreadUtils;

public class Polling {

    private int millis;
    private Runnable run;

    private Thread thread;

    public Polling(int millis, Runnable run) {
        this.millis = millis;
        this.run = run;
    }

    private boolean isStart = false;
    public void start(){
        if (thread != null && thread.isAlive())
            breakPolling();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (true)
                    {
                        Thread.sleep(millis);

                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                run.run();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        isStart  = true;
    }

    public boolean isStart(){
        return isStart;
    }

    public boolean isPolling(){
        return thread != null && !thread.isInterrupted();
    }

    public void breakPolling(){
        if (thread != null)     thread.interrupt();
    }

}
