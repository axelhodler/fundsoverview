package org.xorrr.financegrabber.retrieval;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestInfoRetrieval {

    private Document getDocumentFromHtmlFile() throws IOException {
        URL fileUrl = getClass().getResource("/example.html");
        File file = new File(fileUrl.getFile());

        Document doc = Jsoup.parse(file, "UTF-8");

        return doc;
    }
}
