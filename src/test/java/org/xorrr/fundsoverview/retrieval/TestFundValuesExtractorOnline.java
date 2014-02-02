package org.xorrr.fundsoverview.retrieval;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.xorrr.fundsoverview.helpers.IntegrationTest;
import org.xorrr.fundsoverview.retrieval.FidelityFundDocumentAccessor;
import org.xorrr.fundsoverview.retrieval.FundValuesExtractor;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;

@Category(IntegrationTest.class)
public class TestFundValuesExtractorOnline {

    private FundValuesExtractor extractor; 

    @Before
    public void setUp() throws IOException, InvalidIsinException {
        this.extractor = new FundValuesExtractor();
        FidelityFundDocumentAccessor da = new FidelityFundDocumentAccessor();
        Document doc = da.getDocumentForIsin("LU0049112450");

        extractor.useDocument(doc);
    }

    @Test(expected = InvalidIsinException.class)
    public void testFidelityDocumentRetrieverWithWrongIsin()
            throws IOException, InvalidIsinException {
        new FidelityFundDocumentAccessor().getDocumentForIsin("foobarbaz");
    }

    @Test
    public void testGettingTheDifferentValues() {
        assertEquals("Extracting Name", "Fidelity Funds - Pacific Fund A (USD)",
                extractor.extractName());
        assertTrue("Extracting Price", extractor.extractPrice().length() > 2);
        assertTrue("Extracting CurrentGrowth", extractor.extractCurrentYearGrowth().length() > 2);
        assertTrue("Extracting OneYearGrowth", extractor.extractOneYearGrowth().length() > 2);
        assertTrue("Extracting ThreeYearGrowth", extractor.extractThreeYearGrowth().length() > 2);
        assertTrue("Extracting FiveYearGrowth", extractor.extractFiveYearGrowth().length() > 2);
    }
}
