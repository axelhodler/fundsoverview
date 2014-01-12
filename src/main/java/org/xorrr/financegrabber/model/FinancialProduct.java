package org.xorrr.financegrabber.model;

import org.jsoup.nodes.Document;

public class FinancialProduct {

    private Document doc;

    public FinancialProduct(Document doc) {
        this.doc = doc;
    }

    public String getName() {
        String name = doc.getElementsByAttributeValue("class", "info").get(0)
                .child(0).text();
        return name;
    }

    public String getLastPrice() {
        String lastPrice = doc
                .getElementsByAttributeValue("class",
                        "column-datavalue right lastColOfRow first").get(0)
                .child(0).text();
        return lastPrice;
    }

}
