package com.zcyk.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author WuJieFeng
 * @date 2020/5/7 9:23
 */
@Data
@Accessors(chain = true)
public class UnitprojectGis {

    String value;

    String label;

    List<UnitprojectGisBim>children;
}
