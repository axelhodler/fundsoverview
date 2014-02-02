package org.xorrr.fundsoverview.model;

import java.util.List;

public interface FundsDatastore {

    void saveProduct(FundProduct fp);

    List<FundProduct> getAllProducts();

    void deleteProductById(String id);
}
