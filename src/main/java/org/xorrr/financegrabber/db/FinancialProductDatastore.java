package org.xorrr.financegrabber.db;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.xorrr.financegrabber.model.BasicFinancialProduct;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
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

    public List<BasicFinancialProduct> getAllProducts() {
        List<BasicFinancialProduct> allProducts = new ArrayList<BasicFinancialProduct>();

        DBCursor cur = client.getDB("financegrabber")
                .getCollection("FinancialProducts").find();
        iterateCursorAndAddEntriesToProducts(allProducts, cur);

        return allProducts;
    }

    private void iterateCursorAndAddEntriesToProducts(
            List<BasicFinancialProduct> allProducts, DBCursor cur) {
        while (cur.hasNext()) {
            BasicFinancialProduct curProduct = new BasicFinancialProduct();
            curProduct.setWkn(cur.next().get("wkn").toString());
            allProducts.add(curProduct);
        }
    }

    public void deleteProductById(String id) {
        client.getDB("financegrabber").getCollection("FinancialProducts")
                .remove(new BasicDBObject("_id", new ObjectId(id)));
    }
}
