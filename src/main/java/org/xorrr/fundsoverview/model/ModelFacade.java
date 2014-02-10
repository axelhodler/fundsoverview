package org.xorrr.fundsoverview.model;

import java.io.IOException;
import java.util.List;

import org.xorrr.fundsoverview.retrieval.InvalidIsinException;

public interface ModelFacade {

    Fund getFund(String isin) throws IOException, InvalidIsinException;

    void addFund(Fund fp);

    List<Fund> getFunds();

    void deleteFund(String isin);

    boolean checkIfIsinAlreadyAdded(String isin);
}
