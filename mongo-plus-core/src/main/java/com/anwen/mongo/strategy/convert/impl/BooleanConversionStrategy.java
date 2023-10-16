package com.anwen.mongo.strategy.convert.impl;

import com.anwen.mongo.strategy.convert.ConversionStrategy;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Boolean类型转换策略
 *
 * @author JiaChaoYang
 **/
public class BooleanConversionStrategy implements ConversionStrategy {
    @Override
    public void convertValue(Field field, Object obj, Object fieldValue) throws IllegalAccessException {
        String value = String.valueOf(fieldValue);
        if (!Objects.equals(value, "true") && !Objects.equals(value, "false")){
            boolean matches = Pattern.compile("-?[0-9]+\\.?[0-9]*").matcher(value).matches();
            if (matches){
                value = String.valueOf(Integer.parseInt(value) > 0);
            }else {
                throw new IllegalAccessException(field.getName()+" Not a Boolean type");
            }
        }
        field.set(obj, Boolean.parseBoolean(value));
    }
}
