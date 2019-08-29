package org.grant.common;

import lombok.Data;

/**
 * grant
 * 26/8/2019 11:08 AM
 * 描述：列属性
 */
@Data
public class ColumnProps {
    private String columnName;//字段名
    private String typeName;//字段类型
    private Integer columnSize;//字段长度
    private Integer decimalDigits;//小数位
    private String isNullable;//是否可为空 YES/NO
    private Boolean isPrimary; // 是否主键
    private String isAutoincrement; //是否自增长 YES/NO

}
