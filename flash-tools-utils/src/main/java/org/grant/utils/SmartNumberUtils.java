package org.grant.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * grant
 * 30/8/2019 11:11 AM
 * 描述：
 */
public class SmartNumberUtils {

    /**
     * <pre>
     *     createBigDecimal(null, 100) = 100
     *     createBigDecimal("", 100) = 100
     *     createBigDecimal(100%, 100) = 1
     *     createBigDecimal(100-, 100) = -100
     *     createBigDecimal(100+, 100) = 100
     * </pre>
     * @param val
     * @return
     */
    public static final BigDecimal createBigDecimal(String val, BigDecimal def){
        if (StringUtils.isEmpty(val)) return def;
        BigDecimal bval = null;
        if (val.contains("%")){
            val = StringUtils.replaceAll(val, "%", "");
            bval = new BigDecimal(val).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        }else if (val.contains("+")) {
            val = StringUtils.replaceAll(val, "+", "");
            bval = new BigDecimal(val);
        }else if (val.contains("-")) {
            val = "-" + StringUtils.replaceAll(val, "-", "");
            bval = new BigDecimal(val);
        }else {
            bval = new BigDecimal(val);
        }

        return bval;
    }

    public static final BigDecimal createBigDecimal(String val){
       return createBigDecimal(val, BigDecimal.ZERO);
    }
}
