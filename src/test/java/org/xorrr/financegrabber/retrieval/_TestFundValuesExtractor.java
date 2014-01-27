package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class _TestFundValuesExtractor {

    private FundValuesExtractor extractor;

    @Before
    public void setUp() throws IOException {
        this.extractor = new FundValuesExtractor();
    }

    @Test
    public void testGettingThePrice() {
        assertEquals("27,74$", extractor.extractPrice());
    }

    @Test
    public void testGettingTheName() {
        assertEquals("Fidelity Funds - Pacific Fund A (USD)",
                extractor.extractName());
    }
}
