package org.grant.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * grant
 * 26/8/2019 11:12 AM
 * 描述：
 */
@Data
public class TableProps {
    private String tableName;
    private List<ColumnProps> columns = new ArrayList<>();
}
