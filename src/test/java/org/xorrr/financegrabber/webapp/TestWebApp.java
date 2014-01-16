package org.xorrr.financegrabber.webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class TestWebApp {

    @Test
    public void testInitialPageStructure() throws Exception {
        WebClient webClient = new WebClient();

        HtmlPage page = webClient
                .getPage("http://localhost:8080/financegrabber");

        webClient.waitForBackgroundJavaScript(5000);

        HtmlDivision panelDiv = page.getHtmlElementById("add_fund_panel");
        HtmlTextInput fundIdDiv = page.getHtmlElementById("add_fund_id_field");
        HtmlDivision addFundButtonDiv = page.getHtmlElementById("add_fund_button");

        assertEquals("financegrabber", page.getTitleText());
        assertNotNull(panelDiv);
        assertNotNull(fundIdDiv);
        assertNotNull(addFundButtonDiv);
        assertEquals("Add fond", addFundButtonDiv.getFirstChild().getFirstChild().asText());

        webClient.closeAllWindows();
    }
}
