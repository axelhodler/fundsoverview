package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

public class TestOnlineFundRetrieval {

    String url = "https://fww.biz/fidelity/direkt/fondsportraet/?&PARENT=https%3A//www.fidelity.de/de/fonds/fonds-detailansicht.page%3FISIN%3DLU0049112450&ISIN=LU0049112450";

    @Test
    public void testNameRetrieval() throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
        Element e = doc.getElementsByClass("fondIndexInfo").get(0);
        assertEquals("Fidelity Funds - Pacific Fund A (USD)", e
                .getElementsByClass("data_cont").html());
    }
}
