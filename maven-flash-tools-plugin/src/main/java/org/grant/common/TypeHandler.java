package org.grant.common;

/**
 * grant
 * 26/8/2019 5:07 PM
 * 描述：
 */
public class TypeHandler {

    public static String toJava(String dbType){
        String ret = "";
        if ("varchar".equalsIgnoreCase(dbType) ||
            "longvarchar".equalsIgnoreCase(dbType)){
            ret = "java.lang.String";
        }else if("integer".equalsIgnoreCase(dbType)){
            ret = "java.lang.Integer";
        }else if("timestamp".equalsIgnoreCase(dbType)){
            ret = "java.util.Date";
        }else if("boolean".equalsIgnoreCase(dbType)){
            ret = "java.lang.Boolean";
        }else if("bigint".equalsIgnoreCase(dbType)){
            ret = "java.lang.Long";
        }else if("tinyint".equalsIgnoreCase(dbType)){
            ret = "java.lang.Byte";
        }else if("smallint".equalsIgnoreCase(dbType)){
            ret = "java.lang.Short";
        }else if("double".equalsIgnoreCase(dbType)){
            ret = "java.lang.Double";
        } else if("decimal".equalsIgnoreCase(dbType)) {
            ret = "java.math.BigDecimal";
        }
        return ret;
    }

}
