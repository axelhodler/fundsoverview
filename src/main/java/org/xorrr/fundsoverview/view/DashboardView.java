package org.xorrr.fundsoverview.view;

import java.util.List;

import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.presenter.DashboardViewHandler;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;


public interface DashboardView extends View {

    public void init();

    public void setHandler(DashboardViewHandler handler);

    public Button getAddFundBtn();

    public List<Button> getDeleteFundButtons();

    public void displayFunds(List<Fund> funds);

    public Table getFundTable();
}
