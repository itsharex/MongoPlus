package com.anwen.mongo.execute;

import com.anwen.mongo.context.MongoTransactionContext;
import com.anwen.mongo.execute.inject.InjectAbstractExecute;
import com.anwen.mongo.execute.instance.DefaultExecute;
import com.anwen.mongo.execute.instance.SessionExecute;
import com.anwen.mongo.manager.MongoPlusClient;
import com.anwen.mongo.mapping.MongoConverter;
import com.anwen.mongo.proxy.ExecutorProxy;
import com.mongodb.client.ClientSession;

import java.lang.reflect.Proxy;
import java.util.Optional;

/**
 * 执行器工厂
 * @author JiaChaoYang
 * @project mongo-plus
 * @date 2023-12-28 10:55
 **/
public class ExecutorFactory {

    private MongoPlusClient mongoPlusClient;

    private MongoConverter mongoConverter;

    public ExecutorFactory() {
    }

    public ExecutorFactory(MongoPlusClient mongoPlusClient, MongoConverter mongoConverter){
        this.mongoPlusClient = mongoPlusClient;
        this.mongoConverter = mongoConverter;
    }

    public Execute getExecute(){
        ClientSession clientSessionContext = MongoTransactionContext.getClientSessionContext();
        Execute execute = Optional.ofNullable(clientSessionContext)
                .map(clientSession -> (Execute) new SessionExecute(clientSession))
                .orElseGet(DefaultExecute::new);
        Class<? extends Execute> clazz = execute.getClass();
        return (Execute) Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),new ExecutorProxy(execute));

    }

    public InjectAbstractExecute getInjectExecute(String database){
        ClientSession clientSessionContext = MongoTransactionContext.getClientSessionContext();
        Execute execute = Optional.ofNullable(clientSessionContext)
                .map(clientSession -> (Execute) new SessionExecute(clientSession))
                .orElseGet(DefaultExecute::new);
        Class<? extends Execute> clazz = execute.getClass();
        return new InjectAbstractExecute(mongoPlusClient.getCollectionManager(database), (Execute) Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),new ExecutorProxy(execute)),mongoConverter);
    }

}
