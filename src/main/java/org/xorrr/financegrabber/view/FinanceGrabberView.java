package org.xorrr.financegrabber.view;

import org.xorrr.financegrabber.presenter.FinanceGrabberViewHandler;

import com.vaadin.navigator.View;


public interface FinanceGrabberView extends View {

    void init();

    public void addHandler(FinanceGrabberViewHandler handler);
}
