package org.xorrr.financegrabber.retrieval;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FidelityFundDocument {

    private Document doc;

    public FidelityFundDocument(String isin) throws IOException, InvalidIsinException {
        String urlBase = "https://fww.biz/fidelity/direkt/fondsportraet/?&PARENT="
                + "https%3A//www.fidelity.de/de/fonds/fonds-detailansicht.page%3FISIN%3D";
        String urlToFormat = "%s&ISIN=%s";
        String formattedUrlPart = String.format(urlToFormat, isin, isin);

        String url = urlBase + formattedUrlPart;
        this.doc = Jsoup.connect(url).userAgent("Mozilla").get();
        if (this.doc.body().html().equals("Invalid value for parameter ISIN."))
            throw new InvalidIsinException("The provided ISIN was invalid");

    }

    public Document getDocument() {
        return this.doc;
    }
}
