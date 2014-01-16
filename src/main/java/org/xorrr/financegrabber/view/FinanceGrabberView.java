package org.xorrr.financegrabber.view;


public interface FinanceGrabberView {

    interface FinanceGrabberViewListener {
        void buttonClick(String string);
    }
    public void addListener(FinanceGrabberViewListener listener);

    public String getFundId();
}
