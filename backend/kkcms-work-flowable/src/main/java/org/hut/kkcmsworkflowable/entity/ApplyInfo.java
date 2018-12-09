package org.hut.kkcmsworkflowable.entity;

import lombok.*;

/**
 * Created by hutwanghui on 2018/12/9.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApplyInfo {
    private String name;
    private int age;
    private Address familyAddress;
}
