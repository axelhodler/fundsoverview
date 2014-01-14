package org.xorrr.financegrabber.db;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.xorrr.financegrabber.model.BasicFinancialProduct;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class FinancialProductDatastore {

    private DBCollection col;

    public FinancialProductDatastore(MongoClient client) {
        this.col = client.getDB(DbProperties.DB)
                .getCollection(DbProperties.COL);
    }

    public void saveProduct(BasicFinancialProduct bfp) {
        this.col.save(new BasicDBObject(DbProperties.WKN, bfp.getWkn()));
    }

    public List<BasicFinancialProduct> getAllProducts() {
        List<BasicFinancialProduct> allProducts = new ArrayList<BasicFinancialProduct>();

        DBCursor cur = col.find();
        iterateCursorAndAddEntriesToProducts(allProducts, cur);

        return allProducts;
    }

    private void iterateCursorAndAddEntriesToProducts(
            List<BasicFinancialProduct> allProducts, DBCursor cur) {
        while (cur.hasNext()) {
            BasicFinancialProduct curProduct = new BasicFinancialProduct.Builder()
                    .wkn(cur.next().get(DbProperties.WKN).toString()).build();

            allProducts.add(curProduct);
        }
    }

    public void deleteProductById(String id) {
        col.remove(new BasicDBObject(DbProperties.ID, new ObjectId(id)));
    }
}
