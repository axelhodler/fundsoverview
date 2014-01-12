package org.xorrr.financegrabber;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class InfoGrabber {

    public Document getDocumentForId(String id) {
        String test = "http://www.boerse-frankfurt.de/de/suche/"
                + "ergebnis?order_by=wm_vbfw.name&name_isin_wkn=" + id;

        Document doc = null;
        try {
            doc = Jsoup.connect(test).userAgent("Mozilla").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return doc;
    }

    public String getName(Document doc) {
        String name = doc.getElementsByAttributeValue("class", "info").get(0)
        .child(0).text();
        return name;
    }

}
