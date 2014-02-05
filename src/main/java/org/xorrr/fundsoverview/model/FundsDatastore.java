package org.xorrr.fundsoverview.model;

import java.util.List;

public interface FundsDatastore {

    void saveFund(Fund fp);

    List<Fund> getAllFunds();

    void deleteFundById(String id);

    void deleteFundByIsin(String isin);

    boolean checkIfIsinAlreadyAdded(String isin);
}
