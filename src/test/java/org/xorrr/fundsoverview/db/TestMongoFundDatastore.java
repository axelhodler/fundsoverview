package org.xorrr.fundsoverview.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.helpers.IntegrationTest;
import org.xorrr.fundsoverview.model.Fund;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;

@Category(IntegrationTest.class)
public class TestMongoFundDatastore {

    private MongoClient client;
    private MongoFundsDatastore ds;
    private DBCollection col;
    private String testIsin = "testIsin";
    private static MongodExecutable mongodExe;

    private void createAndSaveTwoBasicFinancialProducts() {
        Fund fp = new Fund.Builder().isin(testIsin).build();
        Fund fp2 = new Fund.Builder().isin(testIsin + "2").build();

        this.ds.saveFund(fp);
        this.ds.saveFund(fp2);
    }

    @BeforeClass
    public static void setUpEmbeddedMongo() throws Exception {
        mongodExe = EmbeddedMongo.getEmbeddedMongoExecutable();
        mongodExe.start();
    }

    @Before
    public void setUp() throws Exception {
        this.client = new MongoClient("localhost", EmbeddedMongoProperties.PORT);
        this.col = this.client.getDB(System.getenv(EnvironmentVariables.DB))
                .getCollection(DbProperties.COL);
        this.ds = new MongoFundsDatastore(client);
    }

    @Test
    public void testAddingFinancialProduct() throws Exception {
        Fund fp = new Fund.Builder().isin(testIsin).build();

        this.ds.saveFund(fp);

        DBObject dbo = col.findOne(new BasicDBObject(DbProperties.ISIN,
                testIsin));
        assertEquals(testIsin, dbo.get(DbProperties.ISIN));
    }

    @Test
    public void testGettingSavedFinancialProducts() throws Exception {
        createAndSaveTwoBasicFinancialProducts();

        assertEquals(2, this.ds.getAllFunds().size());
    }

    @Test
    public void testDeletingSavedFinancialProduct() throws Exception {
        createAndSaveTwoBasicFinancialProducts();
        DBCursor curs = col
                .find(new BasicDBObject(DbProperties.ISIN, testIsin));
        String id = null;
        while (curs.hasNext()) {
            id = curs.next().get(DbProperties.ID).toString();
        }

        this.ds.deleteFundById(id);
        assertEquals(1, this.ds.getAllFunds().size());
    }

    @Test
    public void deleteSavedFinancialProductByIsin() {
        createAndSaveTwoBasicFinancialProducts();
        assertEquals(2, this.ds.getAllFunds().size());
        this.ds.deleteFundByIsin(testIsin);
        assertEquals(1, this.ds.getAllFunds().size());
    }

    @Test
    public void checkIfIsinAdded() {
        this.ds.saveFund(new Fund.Builder().isin(testIsin).build());
        assertTrue(this.ds.checkIfIsinAlreadyAdded(testIsin));
        assertFalse(this.ds.checkIfIsinAlreadyAdded("12345"));
    }

    @After
    public void dropDatabase() throws Exception {
        client.dropDatabase(System.getenv(EnvironmentVariables.DB));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        mongodExe.stop();
    }
}
