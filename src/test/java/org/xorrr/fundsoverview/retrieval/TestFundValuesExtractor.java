package org.xorrr.fundsoverview.retrieval;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.retrieval.FundValuesExtractor;

public class TestFundValuesExtractor {

    private FundValuesExtractor extractor;

    private Document getDocumentFromHtmlFile() throws IOException {
        URL fileUrl = getClass().getResource("/example.html");
        File file = new File(fileUrl.getFile());
        Document doc = Jsoup.parse(file, "UTF-8");
        
        return doc;
    }

    @Before
    public void setUp() throws IOException {
        this.extractor = new FundValuesExtractor();
        extractor.useDocument(getDocumentFromHtmlFile());
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

    @Test
    public void testGettingGrowthThisYear() {
        assertEquals("3,69%", extractor.extractCurrentYearGrowth());
    }

    @Test
    public void testGettingGrowthOneYear() {
        assertEquals("26,35%", extractor.extractOneYearGrowth());
    }

    @Test
    public void testGettingGrowthThreeYears() {
        assertEquals("26,89%", extractor.extractThreeYearGrowth());
    }

    @Test
    public void testGettingGrowthFiveYears() {
        assertEquals("184,73%", extractor.extractFiveYearGrowth());
    }
}
