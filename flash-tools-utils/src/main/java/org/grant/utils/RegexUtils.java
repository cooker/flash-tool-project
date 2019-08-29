package org.grant.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * grant
 * 28/8/2019 5:17 PM
 * 描述：
 */
public class RegexUtils {

    public static final Pattern CHINESE = Pattern.compile("[\\u4e00-\\u9fa5]");
    public static final Pattern EMAIL = Pattern.compile("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");
    public static final Pattern PHONE = Pattern.compile("0?(13|14|15|17|18|19)[0-9]{9}");
    public static final Pattern IP = Pattern.compile("((?:(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d)\\\\.){3}(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d))");

    public static List<String> findChinese(String str){
        return matchers(CHINESE, str);
    }

    public static List<String> findEmail(String str){
        return matchers(EMAIL, str);
    }

    public static List<String> findPhone(String str){
        return matchers(PHONE, str);
    }

    public static List<String> findIp(String str){
        return matchers(IP, str);
    }

    public static List<String> matchers(Pattern pattern, String str){
        Matcher matcher = pattern.matcher(str);
        List<String> strs = new ArrayList<>();
        while (matcher.find()){
            strs.add(matcher.group());
        }
        return strs;
    }
}
