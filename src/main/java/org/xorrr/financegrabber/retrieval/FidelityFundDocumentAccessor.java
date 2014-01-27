package org.xorrr.financegrabber.retrieval;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FidelityFundDocumentAccessor implements FundDocumentAccessor {

    private String urlBase = "https://fww.biz/fidelity/direkt/fondsportraet/?&PARENT="
            + "https%3A//www.fidelity.de/de/fonds/fonds-detailansicht.page%3FISIN%3D";
    private String urlToFormat = "%s&ISIN=%s";

    public Document getDocumentForIsin(String isin) throws IOException, InvalidIsinException{
        String url = urlBase + String.format(urlToFormat, isin, isin);
        Document doc = getDocumentWithJsoup(url);

        checkIsinValidity(doc);
        return doc;
    }

    private void checkIsinValidity(Document doc) throws InvalidIsinException {
        if (doc.body().html().equals("Invalid value for parameter ISIN."))
            throw new InvalidIsinException("The provided ISIN was invalid");
    }

    private Document getDocumentWithJsoup(String url) throws IOException {
        return Jsoup.connect(url).userAgent("Mozilla").get();
    }


}
