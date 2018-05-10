package com.kk.api.entity.useraction;

import java.util.List;

/**
 * Created by msi- on 2018/5/6.
 */
public class echarts_mouth {
    private List<Integer> mouth_result;

    @Override
    public String toString() {
        return "echarts_mouth{" +
                "mouth_result='" + mouth_result + '\'' +
                '}';
    }

    public List<Integer> getMouth_result() {
        return mouth_result;
    }

    public void setMouth_result(List<Integer> mouth_result) {
        this.mouth_result = mouth_result;
    }
}
