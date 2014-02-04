package org.xorrr.fundsoverview.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xorrr.fundsoverview.model.FundProduct;
import org.xorrr.fundsoverview.model.ModelFacade;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;
import org.xorrr.fundsoverview.view.DashboardView;

public class DashboardPresenter implements DashboardViewHandler {

    DashboardView view;
    ModelFacade model;

    public DashboardPresenter(DashboardView view, ModelFacade model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void addFund(FundProduct fp) {
        try {
            model.getBasicFinancialProduct(fp.getIsin());
            model.addFund(fp);
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

    @Override
    public void deleteFund(String isin) {
        model.deleteFund(isin);
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
            FundProduct fp = model.getBasicFinancialProduct(fund
                    .getIsin());
            fundsWithInfos.add(fp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidIsinException e) {
            e.printStackTrace();
        }
    }
}
