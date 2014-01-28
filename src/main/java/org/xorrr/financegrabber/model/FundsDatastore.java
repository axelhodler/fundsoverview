package org.xorrr.financegrabber.model;

import java.util.List;

public interface FundsDatastore {

    void saveProduct(BasicFinancialProduct bfp);

    List<BasicFinancialProduct> getAllProducts();

    void deleteProductById(String id);
}
