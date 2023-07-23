package com.anwen.mongo.toolkit;

import com.anwen.mongo.sql.query.LambdaQueryChainWrapper;
import com.anwen.mongo.sql.SqlOperation;
import com.anwen.mongo.sql.update.LambdaUpdateChainWrapper;

/**
 * 快速构建链式调用
 * @author JiaChaoYang
 * @date 2023/6/24/024 2:27
*/ 
public final class ChainWrappers {

    public static <T> LambdaQueryChainWrapper<T> lambdaQueryChain(Class<T> clazz,SqlOperation<T> sqlOperation){
        return new LambdaQueryChainWrapper<>(clazz,sqlOperation);
    }

    public static <T> LambdaUpdateChainWrapper<T> lambdaUpdateChain(SqlOperation<T> sqlOperation){
        return new LambdaUpdateChainWrapper<>(sqlOperation);
    }

    public static <T> LambdaQueryChainWrapper<T> lambdaQueryChain(SqlOperation<T> sqlOperation){
        return new LambdaQueryChainWrapper<>(sqlOperation);
    }

}
