package org.xorrr.financegrabber.view;

import java.util.List;

import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.presenter.FinanceGrabberViewHandler;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;


public interface FinanceGrabberView extends View {

    public void init();

    public void setHandler(FinanceGrabberViewHandler handler);

    public Button getAddFundBtn();

    public void displayFunds(List<BasicFinancialProduct> funds);

    public Table getFundTable();
}
