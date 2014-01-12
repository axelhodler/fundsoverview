package org.xorrr.financegrabber;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "org.xorrr.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        UI.getCurrent().getPage().setTitle("financegrabber");
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Table table = new Table("Financial Products");
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Last Price", String.class, null);

        table.addItem(new Object[] {"test", "123123.32"}, new Integer(1));
        table.addItem(new Object[] {"test2", "153123.32"}, new Integer(2));

        table.setId("mainTable");
        layout.addComponent(table);
    }

}
