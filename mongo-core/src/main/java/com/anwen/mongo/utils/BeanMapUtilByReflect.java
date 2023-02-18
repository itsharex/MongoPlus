package com.anwen.mongo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anwen.mongo.annotation.table.TableField;
import org.bson.Document;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author JiaChaoYang
 * bean、map操作
 * @since 2023-02-09 15:08
 **/
public class BeanMapUtilByReflect {

    /**
     * 对象转Map
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author JiaChaoYang
     * @since 2023/2/9 15:11
    */
    public static Map<String, Object> beanToMap(Object object) {
        // 默认序列化为数字类型的时间戳
        // String jsonStr = JSON.toJSONString(obj);

        // Fastjson内置了一个默认的日期格式yyyy-MM-dd HH:mm:ss，
        // 可以通过在调用JSON.toJSONString时传入SerializerFeature.WriteDateUseDateFormat来启用。
        // 通过修改默认的时间格式，结合启用默认日期格式，也可以达到按指定日期格式序列化的目的
        // JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        String jsonStr = JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
        return JSON.parseObject(jsonStr, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * map转对象
     * @param map map
     * @param beanClass class
     * @return T
     * @author JiaChaoYang
     * @since 2023/2/9 15:12
    */
    public static <T> T mapToBean(Map<String,Object> map, Class<T> beanClass) throws Exception {
        T object = beanClass.newInstance();
        Class<? super T> superclass = beanClass.getSuperclass();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(object, map.get(field.getName()));
            }
        }
        return object;
    }

    public static <T> List<Document> listToDocumentList(Collection<T> collection){
        List<Document> documentList = new ArrayList<>();
        collection.forEach(c -> {
            documentList.add(new Document(checkTableField(c)));
        });
        return documentList;
    }

    public static <T> Map<String,Object> checkTableField(T entity){
        Map<String,Object> resultMap = new HashMap<>();
        Class<?> entityClass = entity.getClass();
        for (Field field : entityClass.getFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(TableField.class)){
                TableField annotation = field.getAnnotation(TableField.class);
                if (!annotation.exist()){
                    String fieldName = annotation.value() != null ? annotation.value() : field.getName();
                    try {
                        resultMap.put(fieldName,field.get(entity));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return resultMap;
    }

}
