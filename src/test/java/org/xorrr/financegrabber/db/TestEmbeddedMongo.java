package org.xorrr.financegrabber.db;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xorrr.financegrabber.model.BasicFinancialProduct;

import com.mongodb.BasicDBObject;
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

    private static MongodExecutable mongodExecutable = null;
    private static int port = 12345;

    private MongoClient client;
    private FinancialProductDatastore ds;

    @BeforeClass
    public static void setUpEmbeddedMongo() throws Exception {
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6())).build();

        MongodStarter runtime = MongodStarter.getDefaultInstance();

        mongodExecutable = runtime.prepare(mongodConfig);
        mongodExecutable.start();
    }

    @Before
    public void setUp() throws Exception {
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:12345");
        this.client = new MongoClient(uri);
        this.ds = new FinancialProductDatastore(this.client);
    }

    @Test
    public void testAddingFinancialProduct() throws Exception {
        BasicFinancialProduct bfp = new BasicFinancialProduct();
        bfp.setWkn("testWkn");

        this.ds.saveProduct(bfp);

        DBObject dbo = this.client.getDB("financegrabber").getCollection("FinancialProducts")
                .findOne(new BasicDBObject("wkn", "testWkn"));
        assertEquals("testWkn", dbo.get("wkn"));
    }

    @After
    public void dropDatabase() throws Exception {
        client.dropDatabase("financegrabber");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (mongodExecutable != null)
            mongodExecutable.stop();
    }
}
