package com.xq.projectdefine.bean.entity;

import com.xq.projectdefine.bean.behavior.SuccessBehavior;

public class SuccessBean implements SuccessBehavior{

    private boolean isSuccess;

    @Override
    public String toString() {
        return "SuccessBean{" +
                "isSuccess=" + isSuccess +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SuccessBean that = (SuccessBean) o;

        return isSuccess == that.isSuccess;
    }

    @Override
    public int hashCode() {
        return (isSuccess ? 1 : 0);
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
