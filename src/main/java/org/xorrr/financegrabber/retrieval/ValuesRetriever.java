package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ValuesRetriever {

    private Document doc;

    public ValuesRetriever() throws IOException {
        this.doc = getDocumentFromHtmlFile();
    }

    public String getPrice() {
        Element e = doc.getElementsByClass("priceValue").get(0);
        return e.html().replace("&nbsp;", "");
    }

    private Document getDocumentFromHtmlFile() throws IOException {
        URL fileUrl = getClass().getResource("/example.html");
        File file = new File(fileUrl.getFile());

        Document doc = Jsoup.parse(file, "UTF-8");

        return doc;
    }
}
