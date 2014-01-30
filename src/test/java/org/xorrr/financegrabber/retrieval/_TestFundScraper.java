package org.xorrr.financegrabber.retrieval;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.xorrr.financegrabber.model.BasicFinancialProduct;

public class _TestFundScraper {

    private FundDocumentAccessor accessor;
    private FundValuesExtractor extractor;
    private FundScraper scraper;

    @Before
    public void setUp() {
        accessor = mock(FundDocumentAccessor.class);
        extractor = mock(FundValuesExtractor.class);

        this.scraper = new FundScraper(accessor, extractor);
    }

    @Test
    public void canGetBasicFinancialProductForIsin() throws IOException,
            InvalidIsinException {
        String expectedName = "Name";
        String expectedValue = "23";
        String isin = "asdf";

        when(extractor.extractName()).thenReturn(expectedName);
        when(extractor.extractPrice()).thenReturn(expectedValue);

        BasicFinancialProduct bfp = scraper
                .getBasicFinancialProductForIsin(isin);

        verify(accessor, times(1)).getDocumentForIsin(isin);
        verify(extractor, times(1)).useDocument(any(Document.class));
        verify(extractor, times(1)).extractName();
        assertEquals(expectedName, bfp.getName());
        assertEquals(expectedValue, bfp.getValue());
    }

}
