package com.xq.androidfaster.bean.entity;

import com.xq.androidfaster.bean.behavior.ListBehavior;
import java.util.List;

public class ListBean implements ListBehavior {

    protected List list;

    public ListBean() {
    }

    public ListBean(List list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ListBean{" +
                "list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListBean listBean = (ListBean) o;

        return list != null ? list.equals(listBean.list) : listBean.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }

    @Override
    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

}
