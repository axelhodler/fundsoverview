package org.xorrr;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestCase {

    @Ignore
    @Test
    public void testPage() throws Exception {
        final WebClient webClient = new WebClient();

        final HtmlPage page = webClient
                .getPage("http://localhost:8080/financegrabber");

        webClient.waitForBackgroundJavaScript(5000);

        final HtmlDivision div = page.getHtmlElementById("testid");

        assertEquals("financegrabber", page.getTitleText());
        assertEquals("button", div.getAttribute("role"));

        webClient.closeAllWindows();
    }

    @Test
    public void testJsoup() throws Exception {

        String test = "http://www.boerse-frankfurt.de/de/suche/"
                + "ergebnis?order_by=wm_vbfw.name&name_isin_wkn=LU0048578792";

        Document doc = Jsoup.connect(test).userAgent("Mozilla").get();

        assertEquals(doc.getElementsByAttributeValue("class", "info").get(0)
                .child(0).text(), "FF - European Growth");
    }
}
