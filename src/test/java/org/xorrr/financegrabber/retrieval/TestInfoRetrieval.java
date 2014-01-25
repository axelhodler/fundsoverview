package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class TestInfoRetrieval {

    @Test
    public void testGettingThePrice() throws IOException {
        ValuesRetriever ret = new ValuesRetriever();
        assertEquals("27,74$", ret.retrievePrice());
    }

    @Test
    public void testGettingTheName() throws IOException {
        ValuesRetriever ret = new ValuesRetriever();
        assertEquals("Fidelity Funds - Pacific Fund A (USD)",
                ret.retrieveName());
    }
}
