package org.xorrr.fundsoverview.di;

import org.xorrr.fundsoverview.db.MongoFundsDatastore;
import org.xorrr.fundsoverview.model.FundsDatastore;
import org.xorrr.fundsoverview.model.ModelFacade;
import org.xorrr.fundsoverview.model.ModelFacadeImpl;
import org.xorrr.fundsoverview.retrieval.FidelityFundDocumentAccessor;
import org.xorrr.fundsoverview.retrieval.FundDocumentAccessor;
import org.xorrr.fundsoverview.view.DashboardView;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(FundDocumentAccessor.class).to(FidelityFundDocumentAccessor.class);

        bind(FundsDatastore.class).to(MongoFundsDatastore.class);

        bind(ModelFacade.class).to(ModelFacadeImpl.class);

        bind(DashboardView.class).to(DashboardViewImpl.class);
    }
}
