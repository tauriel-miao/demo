package com.tauriel.demo.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * @author qiang.gao
 * @project SmartWeb
 * @descriptions 数据类型工具类
 * @create 2018-04-02 19:42
 **/
@Component
public class DataTypeUtil {
    private static final Map<String, String> dataTypeMap = new HashMap<>();


    private static void initDataType() {
        dataTypeMap.put("java.lang.Integer", "Integer");
        dataTypeMap.put("java.lang.Double", "Double");
        dataTypeMap.put("java.lang.Float", "Float");
        dataTypeMap.put("java.lang.Long", "Long");
        dataTypeMap.put("java.lang.Short", "Short");
        dataTypeMap.put("java.lang.Byte", "Byte");
        dataTypeMap.put("java.lang.Boolean", "Boolean");
        dataTypeMap.put("java.lang.Character", "Character");
        dataTypeMap.put("java.lang.String", "String");
        dataTypeMap.put("int", "int");
        dataTypeMap.put("double", "double");
        dataTypeMap.put("long", "long");
        dataTypeMap.put("short", "short");
        dataTypeMap.put("byte", "byte");
        dataTypeMap.put("boolean", "boolean");
        dataTypeMap.put("char", "char");
        dataTypeMap.put("float", "float");
        dataTypeMap.put("java.util.List", "List");
        dataTypeMap.put("java.util.Map", "Map");
        dataTypeMap.put("java.util.Set", "Set");
        dataTypeMap.put("com.alibaba.fastjson.JSONObject", "JSONObject");
        dataTypeMap.put("java.util.Date", "Date");
        dataTypeMap.put("java.sql.Date", "Date");
    }


    public static String getDataType(String dataClass) {
        initDataType();
        return dataTypeMap.get(dataClass);
    }

    /**
     * 根据数据类型，返回相应格式的数据
     *
     * @param dataType 数据类型
     * @param v        值
     * @return
     */
    public static Object getParamsByClass(String dataType, String v) {
//        String dataType = getDataType(dataClass);
        Object object = null;
        switch (dataType) {
            //todo 引用类型再次分析具体数据类型
            case "JSONObject":
                if (StringUtils.isEmpty(v)) {
                    object = null;
                } else {
                    object = JSONObject.parseObject(v);
                }
                break;
            case "String":
                object = String.valueOf(v == null ? "" : v);
                break;
            case "List":
                if (StringUtils.isEmpty(v)) {
                    object = null;
                } else {
                    v = v.toString().replace("[", "").replace("]", "");
                    //object = java.util.Arrays.asList(v.split(Constant.SPECIAL_SYMBOL_DH));
                }
                break;
            case "int":
                object = Integer.valueOf(v == null ? "0" : v);
                break;
            case "Integer":
                object = Integer.valueOf(v == null ? "0" : v);
                break;
            case "boolean":
                object = Boolean.valueOf(v == null ? "false" : v);
                break;
            case "Boolean":
                object = Boolean.valueOf(v == null ? "false" : v);
                break;
            case "Date":
                if (StringUtils.isNotEmpty(v)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        object = sdf.parse(v);
                    } catch (Exception e) {
                        System.out.println("时间类型转换错误" + e);
                    }
                } else {
                    object = null;
                }
                break;
            case "Double":
                object = Double.valueOf(v == null ? "0" : v);
                break;
            case "double":
                object = Double.valueOf(v == null ? "0" : v);
                break;
            case "Long":
                object = Long.valueOf(v == null ? "0" : v);
                break;
            case "long":
                object = Long.valueOf(v == null ? "0" : v);
                break;
            case "Float":
                object = Float.valueOf(v == null ? "0" : v);
                break;
            case "float":
                object = Float.valueOf(v == null ? "0" : v);
                break;
            case "Short":
                object = Short.valueOf(v == null ? "0" : v);
                break;
            default:
        }
        return object;
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
//        Object v = getParamsByClass("Date", "2018-05-04 17:54:55");
        System.out.println(Double.valueOf("888"));
        Object v = getParamsByClass("Double","1l");
        System.out.println(v);

    }

    public static void getFieldsValue(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
//        initDataType();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                for (String str : dataTypeMap.keySet()) {
                    if (f.getType().getName().equals(str)) {
                        System.out.println("字段：" + f.getName() + " 类型为：" + f.getType().getName() + " 值为：" + f.get(obj));
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
