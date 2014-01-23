package org.xorrr.financegrabber.presenter;

import java.util.List;

import org.xorrr.financegrabber.db.FinancialProductDatastore;
import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.view.FinanceGrabberView;

public class FinanceGrabberPresenter implements FinanceGrabberViewHandler {

    FinanceGrabberView view;
    FinancialProductDatastore ds;

    public FinanceGrabberPresenter(FinanceGrabberView view, FinancialProductDatastore ds) {
        this.view = view;
        this.ds = ds;
    }

    @Override
    public void addFund(BasicFinancialProduct bfp) {
        ds.saveProduct(bfp);
    }

    @Override
    public void showFunds() {
        List<BasicFinancialProduct> funds = ds.getAllProducts();
        view.displayFunds(funds);
    }
}
