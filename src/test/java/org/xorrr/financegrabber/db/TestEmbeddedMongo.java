package org.xorrr.financegrabber.db;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xorrr.financegrabber.model.BasicFinancialProduct;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class TestEmbeddedMongo {

    private MongodExecutable mongodExecutable = null;
    private int port = 12345;

    @Before
    public void setUp() throws Exception {
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(this.port, Network.localhostIsIPv6())).build();

        MongodStarter runtime = MongodStarter.getDefaultInstance();

        this.mongodExecutable = runtime.prepare(mongodConfig);
        mongodExecutable.start();
    }

    @Test
    public void testEmbeddedWithMongoUri() throws Exception {
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:12345");
        MongoClient client = new MongoClient(uri);

        DB db = client.getDB("test");

        DBCollection col = db.createCollection("testCol", new BasicDBObject());
        Date date = new Date();
        col.save(new BasicDBObject("testDoc", date));

        assertEquals(date, col.findOne().get("testDoc"));
    }

    @Test
    public void testAddingFinancialProduct() throws Exception {
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:12345");
        MongoClient client = new MongoClient(uri);
        FinancialProductDatastore ds = new FinancialProductDatastore(client);
        BasicFinancialProduct bfp = new BasicFinancialProduct();
        bfp.setWkn("testWkn");

        ds.saveProduct(bfp);

        DBObject dbo = client.getDB("financegrabber").getCollection("FinancialProducts")
                .findOne(new BasicDBObject("wkn", "testWkn"));
        assertEquals("testWkn", dbo.get("wkn"));
    }

    @After
    public void tearDown() throws Exception {
        if (this.mongodExecutable != null)
            this.mongodExecutable.stop();
    }
}
