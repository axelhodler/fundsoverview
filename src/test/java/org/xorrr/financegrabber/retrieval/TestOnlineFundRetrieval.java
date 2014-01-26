package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class TestOnlineFundRetrieval {

    @Test
    public void testFidelityDocumentRetriever() throws IOException {
        FundDocument doc = new FundDocument("LU0049112450");
        FundInfoRetriever ffr = new FundInfoRetriever(doc.getDocument());
        assertEquals("Fidelity Funds - Pacific Fund A (USD)", ffr.retrieveName());
    }
}
