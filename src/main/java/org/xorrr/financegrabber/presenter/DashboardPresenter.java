package org.xorrr.financegrabber.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xorrr.financegrabber.model.FundProduct;
import org.xorrr.financegrabber.model.ModelFacade;
import org.xorrr.financegrabber.retrieval.InvalidIsinException;
import org.xorrr.financegrabber.view.DashboardView;

public class DashboardPresenter implements DashboardViewHandler {

    DashboardView view;
    ModelFacade model;

    public DashboardPresenter(DashboardView view, ModelFacade model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void addFund(FundProduct bfp) {
        try {
            model.getBasicFinancialProduct(bfp.getIsin());
            model.addFund(bfp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidIsinException e) {
            System.out.println("The provided ISIN is not valid. Fund was not added");
        }
    }

    @Override
    public void showFunds() {
        List<FundProduct> funds = model.getFunds();
        List<FundProduct> fundsWithInfos = new ArrayList<>();

        iterateSavedFunds(funds, fundsWithInfos);
        view.displayFunds(fundsWithInfos);
    }


    @Override
    public void removeFundTableItems() {
        view.getFundTable().removeAllItems();
    }

    private void iterateSavedFunds(List<FundProduct> funds,
            List<FundProduct> fundsWithInfos) {
        for (FundProduct fund : funds) {
            addExtractedToFunds(fundsWithInfos, fund);
        }
    }

    private void addExtractedToFunds(
            List<FundProduct> fundsWithInfos,
            FundProduct fund) {
        try {
            FundProduct bfp = model.getBasicFinancialProduct(fund
                    .getIsin());
            fundsWithInfos.add(bfp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidIsinException e) {
            e.printStackTrace();
        }
    }
}
