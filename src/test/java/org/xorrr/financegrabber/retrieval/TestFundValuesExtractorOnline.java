package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class TestFundValuesExtractorOnline {

    @Test
    public void testFidelityDocumentRetriever() throws IOException {
        FidelityFundDocument doc = new FidelityFundDocument("LU0049112450");
        FundValuesExtractor extractor = new FundValuesExtractor(doc.getDocument());
        assertEquals("Fidelity Funds - Pacific Fund A (USD)", extractor.extractName());
    }
}
