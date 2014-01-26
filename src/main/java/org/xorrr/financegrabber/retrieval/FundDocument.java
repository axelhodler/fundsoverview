package org.xorrr.financegrabber.retrieval;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FundDocument {

    private Document doc;

    public FundDocument(String isin) throws IOException {
        String urlBase = "https://fww.biz/fidelity/direkt/fondsportraet/?&PARENT="
                + "https%3A//www.fidelity.de/de/fonds/fonds-detailansicht.page%3FISIN%3D";
        String urlToFormat = "%s&ISIN=%s";
        String formattedUrlPart = String.format(urlToFormat, isin, isin);

        String url = urlBase + formattedUrlPart;
        this.doc = Jsoup.connect(url).userAgent("Mozilla").get();
    }

    public Document getDocument() {
        return this.doc;
    }
}
