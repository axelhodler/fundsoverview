package org.xorrr.financegrabber.db;

import org.xorrr.financegrabber.model.BasicFinancialProduct;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

public class FinancialProductDatastore {

    private MongoClient client;

    public FinancialProductDatastore(MongoClient client) {
        this.client = client;
    }

    public void saveProduct(BasicFinancialProduct bfp) {
        client.getDB("financegrabber").getCollection("FinancialProducts")
                .save(new BasicDBObject("wkn", bfp.getWkn()));
    }

}
