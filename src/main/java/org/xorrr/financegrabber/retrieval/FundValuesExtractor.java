package org.xorrr.financegrabber.retrieval;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FundValuesExtractor {

    private Document doc;

    public String extractPrice() {
        Element e = doc.getElementsByClass("priceValue").get(0);
        return removeSpaceInValue(e);
    }

    public Object extractName() {
        Element e = doc.getElementsByClass("fondIndexInfo").get(0);
        return e.getElementsByClass("data_cont").html();
    }

    private String removeSpaceInValue(Element e) {
        return e.html().replace("&nbsp;", "");
    }

    public void useDocument(Document doc) {
        this.doc = doc;
    }
}
