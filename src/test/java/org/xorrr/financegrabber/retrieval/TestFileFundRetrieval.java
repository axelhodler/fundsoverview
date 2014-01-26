package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class TestFileFundRetrieval {

    @Test
    public void testGettingThePrice() throws IOException {
        FileFundRetriever ret = new FileFundRetriever();
        assertEquals("27,74$", ret.retrievePrice());
    }

    @Test
    public void testGettingTheName() throws IOException {
        FileFundRetriever ret = new FileFundRetriever();
        assertEquals("Fidelity Funds - Pacific Fund A (USD)",
                ret.retrieveName());
    }
}
