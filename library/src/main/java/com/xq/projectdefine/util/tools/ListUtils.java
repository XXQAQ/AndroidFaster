package com.xq.projectdefine.util.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public final class ListUtils {

    public static boolean isEmpty(Collection collection){
        return collection == null || collection.size()< 1;
    }

    public static ArrayList<File> stringListToFileList(List list_str){
        if (isEmpty(list_str))
            return null;

        ArrayList<File> list = new ArrayList();
        for (Object object : list_str)
        {
            if (object != null && object instanceof String)
                list.add(new File(object.toString()));
        }
        return list;
    }

    public static ArrayList<String> fileListToStringList(List list_file){
        if (isEmpty(list_file))
            return null;

        ArrayList<String> list = new ArrayList();
        for (Object object : list_file)
        {
            if (object != null && object instanceof File)
                list.add(((File)object).getAbsolutePath());
        }
        return list;
    }

    public static double countAvarage(List list) {

        if (isEmpty(list))
            return 0;

        Number sum = 0;

        for (int i=0;i<list.size();i++)
        {
            Number number = (Number) list.get(i);
            sum = number.doubleValue() + sum.doubleValue();
        }

        return  sum.doubleValue() / list.size();
    }

}
