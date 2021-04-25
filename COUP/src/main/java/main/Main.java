package main;

import java.util.*;

import accounts.Group;
import accounts.ReplacmentDB;
import accounts.SessionControl;
import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import servlets.AuthorizationRequestServlet;
import servlets.GroupsInformationRequestServlet;
import servlets.LogInRequestServlet;
import servlets.SignUpRequestServlet;
import templater.RequestMapGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        /*String meme = "{\"login\":\"вапвап\",\"password\":\"вапвапвап\"}";
        Gson gson = new Gson();
        Map <String,String>cool = RequestMapGenerator.entranceData(meme);
        System.out.println(cool);*/

        FilterHolder filterHolder = new FilterHolder(CrossOriginFilter.class);
        filterHolder.setInitParameter("allowedOrigins", "*");
        filterHolder.setInitParameter("allowedMethods", "GET, POST, DELETE");
        SessionControl control = new SessionControl();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignUpRequestServlet(control)), "/auth/signup/*");
        context.addServlet(new ServletHolder(new LogInRequestServlet(control)), "/auth/login/*");
        context.addServlet(new ServletHolder(new AuthorizationRequestServlet(control)), "/auth/*");
        context.addServlet(new ServletHolder(new GroupsInformationRequestServlet(control)), "/groups/getMainInfo/*");
        context.addFilter(filterHolder, "/*", null);
        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }

}
