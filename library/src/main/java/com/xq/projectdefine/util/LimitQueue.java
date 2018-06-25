package com.xq.projectdefine.util;

import java.util.LinkedList;


public class LimitQueue<E> extends LinkedList<E> {

    private int limit; // 队列长度

    public LimitQueue(int limit) {
        this.limit = limit;
    }

    /**
     * 入列：当队列大小已满时，把队头的元素poll掉
     */
    @Override
    public boolean offer(E e) {
        if (size() >= limit)
        {
            poll();
            super.offer(e);
        }
        else
        {
            super.offer(e);
        }
        return true;
    }

    public int getLimit() {
        return limit;
    }

}