package org.xorrr.fundsoverview.model;

import java.util.List;

public interface FundsDatastore {

    void saveFund(FundProduct fp);

    List<FundProduct> getAllFunds();

    void deleteFundById(String id);

    void deleteFundByIsin(String isin);
}
