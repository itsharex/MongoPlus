package com.anwen.mongo.execute.instance;

import com.anwen.mongo.conn.CollectionManager;
import com.anwen.mongo.convert.CollectionNameConvert;
import com.anwen.mongo.convert.DocumentMapperConvert;
import com.anwen.mongo.execute.AbstractExecute;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.CreateIndexOptions;
import com.mongodb.client.model.DropIndexOptions;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

/**
 * session实例
 *
 * @author JiaChaoYang
 * @project mongo-plus
 * @date 2023-12-28 11:03
 **/
public class SessionExecute extends AbstractExecute {

    private final ClientSession clientSession;

    public SessionExecute(CollectionNameConvert collectionNameConvert, CollectionManager collectionManager, ClientSession clientSession) {
        super(collectionNameConvert, collectionManager);
        this.clientSession = clientSession;
    }

    @Override
    public InsertOneResult doSave(Document document, MongoCollection<Document> collection) {
        return collection.insertOne(clientSession,document);
    }

    @Override
    public InsertManyResult doSaveBatch(List<Document> documentList, MongoCollection<Document> collection) {
        return collection.insertMany(clientSession,documentList);
    }

    @Override
    public UpdateResult doUpdateById(BasicDBObject filter, BasicDBObject update, MongoCollection<Document> collection) {
        return collection.updateOne(clientSession,filter,update);
    }

    @Override
    public UpdateResult doUpdateByColumn(Bson filter, Document document, MongoCollection<Document> collection) {
        return collection.updateMany(clientSession,filter,document);
    }

    @Override
    public DeleteResult executeRemove(Bson filterId, MongoCollection<Document> collection) {
        return collection.deleteOne(clientSession,filterId);
    }

    @Override
    public DeleteResult executeRemoveByColumn(Bson filter, MongoCollection<Document> collection) {
        return collection.deleteMany(clientSession,filter);
    }

    @Override
    public DeleteResult executeRemoveBatchByIds(Bson objectIdBson, MongoCollection<Document> collection) {
        return collection.deleteMany(clientSession,objectIdBson);
    }

    @Override
    public FindIterable<Document> doList(MongoCollection<Document> collection) {
        return collection.find(clientSession);
    }

    @Override
    public FindIterable<Document> doList(BasicDBObject basicDBObject, BasicDBObject projectionList, BasicDBObject sortCond, MongoCollection<Document> collection) {
        return collection.find(clientSession,basicDBObject).projection(projectionList).sort(sortCond);
    }

    @Override
    public AggregateIterable<Document> doAggregateList(List<BasicDBObject> aggregateConditionList, MongoCollection<Document> collection) {
        return collection.aggregate(clientSession,aggregateConditionList);
    }

    @Override
    public FindIterable<Document> doGetById(BasicDBObject queryBasic, MongoCollection<Document> collection) {
        return collection.find(clientSession,queryBasic);
    }

    @Override
    public long executeExist(BasicDBObject queryBasic, MongoCollection<Document> collection) {
        return collection.countDocuments(clientSession,queryBasic);
    }

    @Override
    public FindIterable<Document> doGetByIds(BasicDBObject basicDBObject, MongoCollection<Document> collection) {
        return collection.find(clientSession,basicDBObject);
    }

    @Override
    public UpdateResult executeUpdate(BasicDBObject queryBasic, BasicDBObject updateBasic, MongoCollection<Document> collection) {
        return collection.updateMany(clientSession,queryBasic,updateBasic);
    }

    @Override
    public DeleteResult executeRemove(BasicDBObject deleteBasic, MongoCollection<Document> collection) {
        return collection.deleteMany(clientSession,deleteBasic);
    }

    @Override
    public long executeCountByCondition(BasicDBObject basicDBObject, MongoCollection<Document> collection) {
        return collection.countDocuments(clientSession,basicDBObject);
    }

    @Override
    public long doCount(MongoCollection<Document> collection) {
        return collection.countDocuments(clientSession);
    }

    @Override
    public FindIterable<Document> doQueryCommand(BasicDBObject basicDBObject, MongoCollection<Document> collection) {
        return collection.find(clientSession,basicDBObject);
    }

    @Override
    public FindIterable<Document> doGetByColumn(Bson filter, MongoCollection<Document> collection) {
        return collection.find(clientSession,filter);
    }

    @Override
    public String createIndex(Bson bson, MongoCollection<Document> collection) {
        return collection.createIndex(clientSession,bson);
    }

    @Override
    public String createIndex(Bson bson, IndexOptions indexOptions, MongoCollection<Document> collection) {
        return collection.createIndex(clientSession,bson,indexOptions);
    }

    @Override
    public List<String> createIndexes(List<IndexModel> indexes, MongoCollection<Document> collection) {
        return collection.createIndexes(clientSession,indexes);
    }

    @Override
    public List<String> createIndexes(List<IndexModel> indexes, CreateIndexOptions createIndexOptions, MongoCollection<Document> collection) {
        return collection.createIndexes(clientSession,indexes,createIndexOptions);
    }

    @Override
    public List<Document> listIndexes(MongoCollection<Document> collection) {
        return DocumentMapperConvert.indexesIterableToDocument(collection.listIndexes(clientSession));
    }

    @Override
    public void dropIndex(String indexName, MongoCollection<Document> collection) {
        collection.dropIndex(clientSession,indexName);
    }

    @Override
    public void dropIndex(String indexName, DropIndexOptions dropIndexOptions, MongoCollection<Document> collection) {
        collection.dropIndex(clientSession,indexName,dropIndexOptions);
    }

    @Override
    public void dropIndex(Bson keys, MongoCollection<Document> collection) {
        collection.dropIndex(clientSession,keys);
    }

    @Override
    public void dropIndex(Bson keys, DropIndexOptions dropIndexOptions, MongoCollection<Document> collection) {
        collection.dropIndex(clientSession,keys,dropIndexOptions);
    }

    @Override
    public void dropIndexes(MongoCollection<Document> collection) {
        collection.dropIndexes(clientSession);
    }

    @Override
    public void dropIndexes(DropIndexOptions dropIndexOptions, MongoCollection<Document> collection) {
        collection.dropIndexes(clientSession,dropIndexOptions);
    }
}