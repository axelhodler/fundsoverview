package org.xorrr.financegrabber.presenter;

import java.net.UnknownHostException;

import org.xorrr.financegrabber.db.FinancialProductDatastore;
import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.view.FinanceGrabberView;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class FinanceGrabberPresenter implements FinanceGrabberViewHandler {

    FinanceGrabberView view;

    public FinanceGrabberPresenter(FinanceGrabberView view) {
        this.view = view;
    }

    @Override
    public void addFund(String fundId) {
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:12345");
        FinancialProductDatastore ds = null;
        try {
            ds = new FinancialProductDatastore(new MongoClient(uri));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        BasicFinancialProduct bfp = new BasicFinancialProduct.Builder().wkn(
                fundId).build();
        ds.saveProduct(bfp);
    }
}
