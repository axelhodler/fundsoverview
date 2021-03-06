package org.xorrr.fundsoverview.retrieval;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface FundDocumentAccessor {
    public Document getDocumentForIsin(String isin) throws IOException,
            InvalidIsinException;
}
