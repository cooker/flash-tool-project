package org.grant.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * grant
 * 26/8/2019 11:09 AM
 * 描述：数据库属性
 */
@Data
public class DataBaseProps {
    private String dbName;
    private List<String> tables = new ArrayList<>();
}
