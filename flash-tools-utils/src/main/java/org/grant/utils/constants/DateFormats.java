package org.grant.utils.constants;

public enum DateFormats {
    YYYYMM("yyyyMM"),
    YYYMMdd("yyyyMMdd"),
    YYYYMMddhhmmss("yyyyMMddHHmmss"),
    YYYYMMddhhmmssSSS("yyyyMMddHHmmssSSS"),
    YYYY_MM("yyyy-MM"),
    YYYY_MM_dd("yyyy-MM-dd"),
    YYYY_MM_ddHH_mm_ss("yyyy-MM-dd HH:mm:ss"),
    YYYY_MM_ddHH_mm_ssSSS("yyyy-MM-dd HH:mm:ss.SSS"),
    CH_YYYY_MM_ddHH_mm_ss("yyyy年MM月dd日 HH时mm分ss秒");
    private String format;

    private DateFormats(String format){
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }
}
