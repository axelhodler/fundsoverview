package org.xorrr.financegrabber.db;

import java.util.ArrayList;
import java.util.List;

import org.xorrr.financegrabber.model.BasicFinancialProduct;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
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
        while (cur.hasNext()) {
            BasicFinancialProduct curProduct = new BasicFinancialProduct();
            curProduct.setWkn(cur.next().get("wkn").toString());
            allProducts.add(curProduct);
        }

        return allProducts;
    }

}
