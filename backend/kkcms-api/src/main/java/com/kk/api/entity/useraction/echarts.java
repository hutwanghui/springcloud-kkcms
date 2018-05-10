package com.kk.api.entity.useraction;

/**
 * Created by msi- on 2018/4/29.
 */
public class echarts {
    private Integer value;
    private String name;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "echarts{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
