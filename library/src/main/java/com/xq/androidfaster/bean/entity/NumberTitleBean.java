package com.xq.androidfaster.bean.entity;



import com.xq.androidfaster.bean.behavior.NumberTitleBehavior;


public class NumberTitleBean extends TitleBean implements NumberTitleBehavior{

    protected Number number;

    public NumberTitleBean() {
    }

    public NumberTitleBean(CharSequence title, Number number) {
        super(title);
        this.number = number;
    }

    @Override
    public String toString() {
        return "NumberTitleBean{" +
                "title='" + title + '\'' +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NumberTitleBean that = (NumberTitleBean) o;

        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

}
