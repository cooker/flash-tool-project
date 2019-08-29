package org.grant.utils;

import com.esotericsoftware.reflectasm.MethodAccess;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * grant
 * 26/8/2019 1:41 PM
 * 描述：
 */
@Slf4j
public class BeanCopyUtils {

    private static Map<Class<?>, MethodAccess> methodMap = new HashMap<Class<?>, MethodAccess>();
    private static Map<Class<?>, Map<String, Integer>> methodIndexOfGet = new HashMap<>();
    private static Map<Class<?>, Map<String, Integer>> methodIndexOfSet = new HashMap<>();
    private static Map<Class<?>, Map<String, String>> methodIndexOfType = new HashMap<>();

    /**
     * 浅copy类属性,根据属性名匹配，而不是类型+属性匹配，当类型不同且原属性值为null时，不变动目标类此属性值
     *
     * @param desc
     *            接收复制参数的类
     * @param orgi
     *            原始类
     */
    public static void copyProperties(Object desc, Object orgi){
        MethodAccess descMethodAccess = methodAccessFactory(desc);
        MethodAccess orgiMethodAccess = methodAccessFactory(orgi);
        Map<String, Integer> get = methodIndexOfGet.get(orgi.getClass());
        Map<String, Integer> set = methodIndexOfSet.get(desc.getClass());
        Map<String, String> oritypemap = methodIndexOfType.get(orgi.getClass());
        Map<String, String> desctypemap = methodIndexOfType.get(desc.getClass());

        List<String> sameField = null;
        if (get.size() < set.size()) {
            sameField = new ArrayList<>(get.keySet());
            sameField.retainAll(set.keySet());
        } else {
            sameField = new ArrayList<>(set.keySet());
            sameField.retainAll(get.keySet());
        }
        for (String field : sameField) {
            Integer setIndex = set.get(field);
            Integer getIndex = get.get(field);
            String oritype = oritypemap.get(field);
            String desctype = desctypemap.get(field);
            Object value = orgiMethodAccess.invoke(orgi, getIndex);
            try {
                if (!oritype.equalsIgnoreCase(desctype)) {
                    if (value == null) {
                        continue;
                    }
                    switch (desctype) {
                        case "java.lang.String":
                            descMethodAccess.invoke(desc, setIndex.intValue(), value.toString());
                            break;
                        case "java.lang.Integer":
                        case "int":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Integer.valueOf(value.toString()));
                            break;
                        case "java.lang.Long":
                        case "long":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Long.valueOf(value.toString()));
                            break;
                        case "java.lang.Float":
                        case "float":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Long.valueOf(value.toString()));
                            break;
                        case "java.lang.Boolean":
                        case "boolean":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Boolean.valueOf(value.toString()));
                            break;
                        case "java.lang.Double":
                        case "double":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Double.valueOf(value.toString()));
                            break;
                        case "java.lang.Byte":
                        case "byte":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Byte.valueOf(value.toString()));
                            break;
                        case "java.lang.Short":
                        case "short":
                            descMethodAccess.invoke(desc, setIndex.intValue(), Short.valueOf(value.toString()));
                            break;
                        case "java.math.BigDecimal":
                            descMethodAccess.invoke(desc, setIndex.intValue(), new BigDecimal(value.toString()));
                            break;
                        default:
                            break;
                    }
                } else {
                    descMethodAccess.invoke(desc, setIndex.intValue(), value);
                }
            } catch (Exception e) {
                log.error("{} to {} [{}] copy error", orgi.getClass().getName(), desc.getClass().getName(),field, e);
            }
        }
    }

    // double check
    private static MethodAccess methodAccessFactory(Object obj) {
        MethodAccess descMethodAccess = methodMap.get(obj.getClass());
        if (descMethodAccess == null) {
            synchronized (obj.getClass()) {
                descMethodAccess = methodMap.get(obj.getClass());
                if (descMethodAccess != null) {
                    return descMethodAccess;
                }
                Class<?> c = obj.getClass();
                MethodAccess methodAccess = MethodAccess.get(c);
                Field[] fields = c.getDeclaredFields();
                Map<String, Integer> indexofget = new HashMap<>();
                Map<String, Integer> indexofset = new HashMap<>();
                Map<String, String> indexoftype = new HashMap<>();
                for (Field field : fields) {
                    if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) { // 私有非静态
                        String fieldName = captureName(field.getName()); // 获取属性名称
                        int getIndex = methodAccess.getIndex("get" + fieldName); // 获取get方法的下标
                        int setIndex = methodAccess.getIndex("set" + fieldName); // 获取set方法的下标
                        indexofget.put(fieldName, getIndex);
                        indexofset.put(fieldName, setIndex);
                        indexoftype.put(fieldName, field.getType().getName());
                    }
                }
                methodIndexOfGet.put(c, indexofget);
                methodIndexOfSet.put(c, indexofset);
                methodIndexOfType.put(c, indexoftype);
                methodMap.put(c, methodAccess);
                return methodAccess;
            }
        }
        return descMethodAccess;
    }

    private static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] = Character.toUpperCase(cs[0]);
        return String.valueOf(cs);
    }
}
