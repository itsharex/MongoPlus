package com.anwen.mongo.strategy.executor;

import com.anwen.mongo.enums.ExecuteMethodEnum;
import com.anwen.mongo.interceptor.Interceptor;

/**
 * 方法执行策略(解耦逻辑)
 *
 * @author loser
 * @date 2024/4/28
 */
public interface MethodExecutorStrategy {

    /**
     * 方法类型
     */
    ExecuteMethodEnum method();

    /**
     * 执行拦截方法
     */
    void invoke(Interceptor interceptor, Object[] args);

}
