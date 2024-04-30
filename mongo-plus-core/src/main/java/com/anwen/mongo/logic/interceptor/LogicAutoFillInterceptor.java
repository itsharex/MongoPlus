package com.anwen.mongo.logic.interceptor;

import com.anwen.mongo.cache.global.ClassLogicDeleteCache;
import com.anwen.mongo.config.Configuration;
import com.anwen.mongo.interceptor.Interceptor;
import com.anwen.mongo.logic.LogicDeleteHandler;
import com.anwen.mongo.model.LogicDeleteResult;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;
import java.util.Objects;

/**
 * 逻辑删除默认字段拦截器(初始化逻辑未删除字段、建议方案：使用数据库默认字段 > 其次是手动设置 > 配置框架提供拦截器 > 自定义拦截器）)
 *
 * @author loser
 * @date 2024/4/30
 */
public class LogicAutoFillInterceptor implements Interceptor {

    @Override
    public List<Document> executeSave(List<Document> documentList, MongoCollection<Document> collection) {

        Class<?> clazz = LogicDeleteHandler.getBeanClass(collection);
        if (Objects.isNull(clazz)) {
            return documentList;
        }
        if (!ClassLogicDeleteCache.logicDeleteResultHashMap.containsKey(clazz)) {
            Configuration.builder().setLogicFiled(ClassLogicDeleteCache.logicProperty, clazz);
        }
        LogicDeleteResult result = LogicDeleteHandler.mapper().get(clazz);
        if (Objects.nonNull(result)) {
            for (Document document : documentList) {
                document.put(result.getColumn(), result.getLogicNotDeleteValue());
            }
        }
        return documentList;

    }


}
