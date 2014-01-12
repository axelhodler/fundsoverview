package org.xorrr.financegrabber.webapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestWebApp {

    @Test
    public void testPage() throws Exception {
        final WebClient webClient = new WebClient();

        final HtmlPage page = webClient
                .getPage("http://localhost:8080/financegrabber");

        webClient.waitForBackgroundJavaScript(5000);

        final HtmlDivision div = page.getHtmlElementById("mainTable");

        assertEquals("financegrabber", page.getTitleText());
        assertEquals("v-table v-widget", div.getAttribute("class"));

        webClient.closeAllWindows();
    }
}
