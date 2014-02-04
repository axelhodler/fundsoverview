package org.xorrr.fundsoverview.db;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.model.FundsDatastore;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoFundsDatastore implements FundsDatastore {

    private DBCollection col;

    public MongoFundsDatastore(MongoClient client) {
        this.col = client.getDB(DbProperties.DB)
                .getCollection(DbProperties.COL);
    }

    public void saveFund(Fund bfp) {
        this.col.save(new BasicDBObject(DbProperties.ISIN, bfp.getIsin()));
    }

    public List<Fund> getAllFunds() {
        List<Fund> allFunds = new ArrayList<Fund>();

        DBCursor cur = col.find();
        iterateCursorAndAddEntriesToProducts(allFunds, cur);

        return allFunds;
    }

    public void deleteFundById(String id) {
        col.remove(new BasicDBObject(DbProperties.ID, new ObjectId(id)));
    }
    
    public void deleteFundByIsin(String testIsin) {
        col.remove(new BasicDBObject(DbProperties.ISIN, testIsin));
    }

    private void iterateCursorAndAddEntriesToProducts(
            List<Fund> allFunds, DBCursor cur) {
        while (cur.hasNext()) {
            Fund curFund = new Fund.Builder()
                    .isin(cur.next().get(DbProperties.ISIN).toString()).build();

            allFunds.add(curFund);
        }
    }
}
