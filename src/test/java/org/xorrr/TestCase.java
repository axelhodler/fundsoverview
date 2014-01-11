package org.xorrr;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestCase {

    private String assertedName = "FF - European Growth";
    private String testIsin = "LU0048578792";
    private String testWkn = "973270";

    private Document getDocumentFromHtmlFile() throws IOException {
        URL fileUrl = getClass().getResource("/example.html");
        File file = new File(fileUrl.getFile());

        Document doc = Jsoup.parse(file, "UTF-8");

        return doc;
    }

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
        InfoGrabber ig = new InfoGrabber();
        Document doc = ig.getDocumentForId(testIsin);

        String name = ig.getName(doc);
        assertEquals(name, assertedName);

        Document doc2 = ig.getDocumentForId(testWkn);
        assertEquals(ig.getName(doc2), assertedName);
    }

    @Test
    public void testUsingFinanceProduct() throws Exception {
        Document doc = getDocumentFromHtmlFile();

        FinanceProduct fp = new FinanceProduct(doc);

        assertEquals(fp.getName(), "FF - European Growth");
        assertEquals(fp.getLastPrice(), "11,980");
    }
}
