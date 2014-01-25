package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

public class TestInfoRetrieval {

    private Document getDocumentFromHtmlFile() throws IOException {
        URL fileUrl = getClass().getResource("/example.html");
        File file = new File(fileUrl.getFile());

        Document doc = Jsoup.parse(file, "UTF-8");

        return doc;
    }

    @Test
    public void testGettingTheValue() throws IOException {
        Document doc = getDocumentFromHtmlFile();
        
        Element e = doc.getElementsByClass("priceValue").get(0);
        assertEquals("27,74$", e.html().replace("&nbsp;", ""));
    }
}
