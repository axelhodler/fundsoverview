package org.xorrr;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

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

    @Ignore
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

    @Test
    public void testUsingFinanceProduct() {
        File input = new File("example.html");

        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        FinanceProduct fp = new FinanceProduct(doc);

        assertEquals(fp.getName(), "FF - European Growth");
        assertEquals(fp.getLastPrice(), "11,980");
    }
}
