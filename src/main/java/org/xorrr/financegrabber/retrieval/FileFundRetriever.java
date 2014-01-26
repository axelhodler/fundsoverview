package org.xorrr.financegrabber.retrieval;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FileFundRetriever {

    private Document doc;

    public FileFundRetriever() throws IOException {
        this.doc = getDocumentFromHtmlFile();
    }

    public String retrievePrice() {
        Element e = doc.getElementsByClass("priceValue").get(0);
        return removeSpaceInValue(e);
    }

    public Object retrieveName() {
        Element e = doc.getElementsByClass("fondIndexInfo").get(0);
        return e.getElementsByClass("data_cont").html();
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
