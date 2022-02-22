package com.ksf.factory.factorybean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/22 15:08
 */

@Getter
@Setter
@ToString
@Builder
public class FactoryRealBean {
    private String name;
    private String code;
    private String desc;
}
