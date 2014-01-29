package org.xorrr.financegrabber.presenter;

import java.io.IOException;
import java.util.List;

import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.model.ModelFacade;
import org.xorrr.financegrabber.retrieval.InvalidIsinException;
import org.xorrr.financegrabber.view.FinanceGrabberView;

public class FinanceGrabberPresenter implements FinanceGrabberViewHandler {

    FinanceGrabberView view;
    ModelFacade model;

    public FinanceGrabberPresenter(FinanceGrabberView view, ModelFacade model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void addFund(BasicFinancialProduct bfp) {
        model.addFund(bfp);
    }

    @Override
    public void showFunds() {
        List<BasicFinancialProduct> funds = model.getFunds();
        for (BasicFinancialProduct fund : funds) {
            try {
                model.getFundDocument(fund.getWkn());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvalidIsinException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        view.displayFunds(funds);
    }

    @Override
    public void removeFundTableItems() {
        view.getFundTable().removeAllItems();
    }
}
