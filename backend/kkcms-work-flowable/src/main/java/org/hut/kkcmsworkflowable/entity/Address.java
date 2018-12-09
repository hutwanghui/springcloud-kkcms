package org.hut.kkcmsworkflowable.entity;

import lombok.*;

/**
 * Created by hutwanghui on 2018/12/9.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Address {
    private String city;
    private String detail;
    private String distract;
    private String province;
}
