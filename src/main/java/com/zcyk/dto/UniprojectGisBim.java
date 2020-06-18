package com.zcyk.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WuJieFeng
 * @date 2020/5/12 14:25
 */
@Data
@Accessors(chain = true)
public class UniprojectGisBim {
    String label;
    String value;
}
