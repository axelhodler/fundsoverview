package org.xorrr.financegrabber.retrieval;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FundValuesExtractor {

    private Document doc;

    public String extractPrice() {
        Element element = doc.getElementsByClass("priceValue").get(0);
        return removeSpaceInValue(element);
    }

    public String extractName() {
        Element element = doc.getElementsByClass("fondIndexInfo").get(0);
        return element.getElementsByClass("data_cont").html();
    }

    private String removeSpaceInValue(Element element) {
        return element.html().replace("&nbsp;", "");
    }

    public void useDocument(Document doc) {
        this.doc = doc;
    }

    public String extractCurrentYearGrowth() {
        Element element = doc.select("table.productivity").first();
        return element.child(1).child(0).child(1).html();
    }

    public String extractOneYearGrowth() {
        Element element = doc.select("table.productivity").first();
        return element.child(1).child(0).child(2).html();
    }

    public String extractThreeYearGrowth() {
        Element element = doc.select("table.productivity").first();
        return element.child(1).child(0).child(3).html();
    }

    public String extractFiveYearGrowth() {
        Element element = doc.select("table.productivity").first();
        return element.child(1).child(0).child(4).html();
    }
}
