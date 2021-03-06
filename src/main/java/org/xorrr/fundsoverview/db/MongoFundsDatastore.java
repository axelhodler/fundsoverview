package org.xorrr.fundsoverview.db;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.model.FundsDatastore;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoFundsDatastore implements FundsDatastore {

    private DBCollection col;

    @Inject
    public MongoFundsDatastore(MongoClient client) throws UnknownHostException {
        this.col = client.getDB(System.getenv(EnvironmentVariables.DB))
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

    @Override
    public boolean checkIfIsinAlreadyAdded(String isin) {
        DBCursor cur = col.find(new BasicDBObject(DbProperties.ISIN, isin));
        if (cur.length() == 0)
            return false;
        return true;
    }
}
