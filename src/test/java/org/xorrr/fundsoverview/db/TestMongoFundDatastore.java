package org.xorrr.fundsoverview.db;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.xorrr.fundsoverview.db.DbProperties;
import org.xorrr.fundsoverview.db.MongoFundsDatastore;
import org.xorrr.fundsoverview.helpers.IntegrationTest;
import org.xorrr.fundsoverview.model.FundProduct;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Category(IntegrationTest.class)
public class TestMongoFundDatastore {

    private static int port = 12345;

    private MongoClient client;
    private MongoFundsDatastore ds;
    private DBCollection col;

    private void createAndSaveTwoBasicFinancialProducts() {
        FundProduct fp = new FundProduct.Builder().isin(
                "testWkn").build();
        FundProduct fp2 = new FundProduct.Builder().isin(
                "testWkn2").build();

        this.ds.saveProduct(fp);
        this.ds.saveProduct(fp2);
    }

    @BeforeClass
    public static void setUpEmbeddedMongo() throws Exception {
        EmbeddedMongo.startEmbeddedMongo(port);
    }

    @Before
    public void setUp() throws Exception {
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:12345");
        this.client = new MongoClient(uri);
        this.col = this.client.getDB(DbProperties.DB).getCollection(
                DbProperties.COL);
        this.ds = new MongoFundsDatastore(this.client);
    }

    @Test
    public void testAddingFinancialProduct() throws Exception {
        FundProduct fp = new FundProduct.Builder().isin(
                "testWkn").build();

        this.ds.saveProduct(fp);

        DBObject dbo = col.findOne(new BasicDBObject(DbProperties.ISIN,
                "testWkn"));
        assertEquals("testWkn", dbo.get(DbProperties.ISIN));
    }

    @Test
    public void testGettingSavedFinancialProducts() throws Exception {
        createAndSaveTwoBasicFinancialProducts();

        assertEquals(2, this.ds.getAllProducts().size());
    }

    @Test
    public void testDeletingSavedFinancialProduct() throws Exception {
        createAndSaveTwoBasicFinancialProducts();
        DBCursor curs = col.find(new BasicDBObject(DbProperties.ISIN, "testWkn"));
        String id = null;
        while (curs.hasNext()) {
            id = curs.next().get(DbProperties.ID).toString();
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
        EmbeddedMongo.stopEmbeddedMongo();
    }
}
