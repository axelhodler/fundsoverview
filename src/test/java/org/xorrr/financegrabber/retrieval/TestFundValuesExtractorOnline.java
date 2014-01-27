package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.Test;

public class TestFundValuesExtractorOnline {

    @Test
    public void testFidelityDocumentRetriever() throws IOException,
            InvalidIsinException {
        FundDocumentAccessor da = new FundDocumentAccessor();
        Document doc = da.getDocumentForIsin("LU0049112450");
        FundValuesExtractor extractor = new FundValuesExtractor(
                doc);
        assertEquals("Fidelity Funds - Pacific Fund A (USD)",
                extractor.extractName());
    }

    @Test(expected = InvalidIsinException.class)
    public void testFidelityDocumentRetrieverWithWrongIsin()
            throws IOException, InvalidIsinException {
        new FundDocumentAccessor().getDocumentForIsin("foobarbaz");
    }
}
