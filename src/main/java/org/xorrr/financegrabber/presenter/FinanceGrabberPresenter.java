package org.xorrr.financegrabber.presenter;

import java.io.IOException;
import java.util.ArrayList;
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
        List<BasicFinancialProduct> fundsWithInfos = new ArrayList<>();

        iterateSavedFunds(funds, fundsWithInfos);
        view.displayFunds(fundsWithInfos);
    }


    @Override
    public void removeFundTableItems() {
        view.getFundTable().removeAllItems();
    }

    private void iterateSavedFunds(List<BasicFinancialProduct> funds,
            List<BasicFinancialProduct> fundsWithInfos) {
        for (BasicFinancialProduct fund : funds) {
            addExtractedToFunds(fundsWithInfos, fund);
        }
    }

    private void addExtractedToFunds(
            List<BasicFinancialProduct> fundsWithInfos,
            BasicFinancialProduct fund) {
        try {
            BasicFinancialProduct bfp = model.getBasicFinancialProduct(fund
                    .getIsin());
            fundsWithInfos.add(bfp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidIsinException e) {
            e.printStackTrace();
        }
    }
}
