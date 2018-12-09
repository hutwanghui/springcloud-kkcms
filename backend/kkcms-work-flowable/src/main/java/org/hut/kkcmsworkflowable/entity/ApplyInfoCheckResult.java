package org.hut.kkcmsworkflowable.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hutwanghui on 2018/12/9.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyInfoCheckResult {

    //通过与否的标识
    private boolean postCodeResult = false;
}
