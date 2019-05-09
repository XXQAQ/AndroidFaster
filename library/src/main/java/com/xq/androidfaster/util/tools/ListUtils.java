package com.xq.androidfaster.util.tools;

import java.util.Collection;
import java.util.List;

public final class ListUtils {

    private ListUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * @param collection
     * @return 集合是否为空
     */
    public static boolean isEmpty(Collection collection){
        return collection == null || collection.size()< 1;
    }

    /**
     *计算数值集合类型的平均数
     * @param list 数值类型的集合
     * @return 平均数
     */
    public static double countAvarage(List list) {
        if (isEmpty(list))
            return 0;

        Number sum = 0;
        for (int i=0;i<list.size();i++) {
            Number number = (Number) list.get(i);
            sum = number.doubleValue() + sum.doubleValue();
        }
        return  sum.doubleValue() / list.size();
    }
}
