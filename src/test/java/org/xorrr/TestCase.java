package org.xorrr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void testGettingTheName() {
        String assertedName = "FF - European Growth";

        InfoGrabber ig = new InfoGrabber();
        Document doc = ig.getDocumentForId("LU0048578792");

        String name = ig.getName(doc);
        assertEquals(name, assertedName);

        Document doc2 = ig.getDocumentForId("973270");
        assertEquals(ig.getName(doc2), assertedName);
    }
}
