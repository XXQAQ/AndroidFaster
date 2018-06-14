package com.xq.projectdefine.util.tools;
/**
 * Created by xq on 2017/8/7.
 */

public class NumberUtils {

    public static int getRandom(int min,int max)
    {
        int random = (int )((Math.random()*(max-min))+min);
        return random;
    }

    public static int getRandom(int min,int max,int time)
    {
        int random = (int )((Math.random()*(max-min))+min);
        random=countTimeNo(random,time);
        return random;
    }

    private static int countTimeNo(int some,int time)
    {
        while(some>0)
        {
            if(some%time ==0)
                return some;
            else
                some--;
        }
        return 0;
    }
}
