package org.xorrr.fundsoverview;

import javax.servlet.annotation.WebServlet;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = MainUI.class, widgetset = "com.vaadin.DefaultWidgetSet")
public class Launcher extends VaadinServlet {

    private static final long serialVersionUID = -710297323885061567L;

    public static void main(String[] args) throws Exception {
        Server server = new Server(Integer.valueOf(System
                .getenv(EnvironmentVariables.PORT)));
        WebAppContext context = new WebAppContext();

        context.setResourceBase("target/fundsoverview-1.0");
        context.addServlet(new ServletHolder(new Launcher()), "/*");
        server.setHandler(context);

        server.start();
        server.join();
    }
}
