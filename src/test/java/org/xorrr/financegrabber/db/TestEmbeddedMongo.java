package org.xorrr.financegrabber.db;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xorrr.financegrabber.model.BasicFinancialProduct;

import com.mongodb.BasicDBObject;
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

    private static MongodExecutable mongodExecutable = null;
    private static int port = 12345;

    private MongoClient client;
    private FinancialProductDatastore ds;
    private DBCollection col;

    private void createAndSaveTwoBasicFinancialProducts() {
        BasicFinancialProduct bfp = new BasicFinancialProduct();
        bfp.setWkn("testWkn");
        BasicFinancialProduct bfp2 = new BasicFinancialProduct();
        bfp2.setWkn("testWkn2");

        this.ds.saveProduct(bfp);
        this.ds.saveProduct(bfp2);
    }

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
        this.col = this.client.getDB(DbProperties.DB).getCollection(
                DbProperties.COL);
        this.ds = new FinancialProductDatastore(this.client);
    }

    @Test
    public void testAddingFinancialProduct() throws Exception {
        BasicFinancialProduct bfp = new BasicFinancialProduct();
        bfp.setWkn("testWkn");

        this.ds.saveProduct(bfp);

        DBObject dbo = col.findOne(new BasicDBObject(DbProperties.WKN, "testWkn"));
        assertEquals("testWkn", dbo.get(DbProperties.WKN));
    }

    @Test
    public void testGettingSavedFinancialProducts() throws Exception {
        createAndSaveTwoBasicFinancialProducts();

        assertEquals(2, this.ds.getAllProducts().size());
    }

    @Test
    public void testDeletingSavedFinancialProduct() throws Exception {
        createAndSaveTwoBasicFinancialProducts();
        DBCursor cur = col.find(new BasicDBObject(DbProperties.WKN, "testWkn"));
        String id = null;
        while (cur.hasNext()) {
            id = cur.next().get(DbProperties.ID).toString();
        }

        this.ds.deleteProductById(id);
        assertEquals(1, this.ds.getAllProducts().size());
    }

    @After
    public void dropDatabase() throws Exception {
        client.dropDatabase(DbProperties.DB);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (mongodExecutable != null)
            mongodExecutable.stop();
    }
}
