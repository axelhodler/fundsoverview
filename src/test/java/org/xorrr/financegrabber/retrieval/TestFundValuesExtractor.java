package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class TestFundValuesExtractor {

    @Test
    public void testGettingThePrice() throws IOException {
        FundValuesExtractor ret = new FundValuesExtractor();
        assertEquals("27,74$", ret.extractPrice());
    }

    @Test
    public void testGettingTheName() throws IOException {
        FundValuesExtractor ret = new FundValuesExtractor();
        assertEquals("Fidelity Funds - Pacific Fund A (USD)",
                ret.extractName());
    }
}
