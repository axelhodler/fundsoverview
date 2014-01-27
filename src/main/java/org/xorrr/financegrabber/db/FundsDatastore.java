package org.xorrr.financegrabber.db;

import java.util.List;

import org.xorrr.financegrabber.model.BasicFinancialProduct;

public interface FundsDatastore {

    void saveProduct(BasicFinancialProduct bfp);

    List<BasicFinancialProduct> getAllProducts();

    void deleteProductById(String id);
}
