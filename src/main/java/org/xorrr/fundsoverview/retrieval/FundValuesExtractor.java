package org.xorrr.fundsoverview.retrieval;

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

    public void useDocument(Document doc) {
        this.doc = doc;
    }

    public String extractCurrentYearGrowth() {
        return getGrowth().child(1).html();
    }

    public String extractOneYearGrowth() {
        return getGrowth().child(2).html();
    }

    public String extractThreeYearGrowth() {
        return getGrowth().child(3).html();
    }

    public String extractFiveYearGrowth() {
        return getGrowth().child(4).html();
    }

    private Element getGrowth() {
        return getGrowthRateChild(getGrowthTableData());
    }

    private String removeSpaceInValue(Element element) {
        return element.html().replace("&nbsp;", "");
    }

    private Element getGrowthTableData() {
        return doc.select("table.productivity").first();
    }

    private Element getGrowthRateChild(Element element) {
        return element.child(1).child(0);
    }
}
