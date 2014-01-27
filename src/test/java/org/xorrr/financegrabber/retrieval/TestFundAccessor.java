package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Jsoup.class })
public class TestFundAccessor {

    private FundDocumentAccessor fund;
    private String url = "https://fww.biz/fidelity/direkt/fondsportraet/" 
            + "?&PARENT=https%3A//www.fidelity.de/de/fonds/"
            + "fonds-detailansicht.page%3FISIN%3D&ISIN=";

    @Before
    public void setUp() throws IOException {
        this.fund = new FidelityFundDocumentAccessor();

    }

    @Test
    public void isJsoupCorrectlyUsedWhenGettingDocument() throws IOException,
            InvalidIsinException {
        Connection con = mock(Connection.class);
        Document doc = mock(Document.class);
        Element ele = mock(Element.class);

        when(doc.body()).thenReturn(ele);
        when(ele.html()).thenReturn("roflxd");

        PowerMockito.mockStatic(Jsoup.class);
        Mockito.when(con.get()).thenReturn(doc);

        PowerMockito.when(Jsoup.connect(this.url)).thenReturn(con);
        PowerMockito.when(Jsoup.connect(this.url).userAgent(anyString()))
                .thenReturn(con);
        PowerMockito.when(
                Jsoup.connect(this.url).userAgent(anyString()).get())
                .thenReturn(doc);

        assertEquals(doc, fund.getDocumentForIsin(anyString()));
    }
}
