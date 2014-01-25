package org.xorrr.financegrabber.retrieval;

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

    public String retrievePrice() {
        Element e = doc.getElementsByClass("priceValue").get(0);
        return removeSpaceInValue(e);
    }

    private Document getDocumentFromHtmlFile() throws IOException {
        URL fileUrl = getClass().getResource("/example.html");
        File file = new File(fileUrl.getFile());
        Document doc = Jsoup.parse(file, "UTF-8");

        return doc;
    }

    private String removeSpaceInValue(Element e) {
        return e.html().replace("&nbsp;", "");
    }
}
