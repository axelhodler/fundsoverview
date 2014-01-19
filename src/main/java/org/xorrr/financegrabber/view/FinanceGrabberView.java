package org.xorrr.financegrabber.view;

import org.xorrr.financegrabber.presenter.FinanceGrabberViewHandler;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;


public interface FinanceGrabberView extends View {

    void init();

    void addHandler(FinanceGrabberViewHandler handler);
    Button getAddFundBtn();
}
