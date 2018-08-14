package com.xq.projectdefine.bean.entity;



import com.xq.projectdefine.bean.behavior.NumberContentTitleBehavior;


public class NumberContentTitleBean extends TitleBean implements NumberContentTitleBehavior {

    protected CharSequence content;
    protected Number number;

    public NumberContentTitleBean() {
    }

    public NumberContentTitleBean(CharSequence title, CharSequence content, Number number) {
        super(title);
        this.content = content;
        this.number = number;
    }

    @Override
    public String toString() {
        return "NumberContentTitleBean{" +
                "content='" + content + '\'' +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NumberContentTitleBean that = (NumberContentTitleBean) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    public CharSequence getContent() {
        return content;
    }

    public void setContent(CharSequence content) {
        this.content = content;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

}
