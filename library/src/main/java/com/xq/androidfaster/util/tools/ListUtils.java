package com.xq.androidfaster.util.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public final class ListUtils {

    /**
     * @param collection
     * @return 集合是否为空
     */
    public static boolean isEmpty(Collection collection){
        return collection == null || collection.size()< 1;
    }

    /**
     * String集合转File集合
     * @param list_str
     * @return file
     */
    public static ArrayList<File> stringListToFileList(List list_str){
        if (isEmpty(list_str))
            return null;

        ArrayList<File> list = new ArrayList();
        for (Object object : list_str) {
            if (object != null && object instanceof String)
                list.add(new File(object.toString()));
        }
        return list;
    }

    /**
     * File集合转String集合
     * @param list_file
     * @return
     */
    public static ArrayList<String> fileListToStringList(List list_file){
        if (isEmpty(list_file))
            return null;

        ArrayList<String> list = new ArrayList();
        for (Object object : list_file) {
            if (object != null && object instanceof File)
                list.add(((File)object).getAbsolutePath());
        }
        return list;
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
