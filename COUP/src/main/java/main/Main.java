package main;

import accounts.SessionControl;
import dataProcessing.RawDataAdapter;
import interactDB.DBManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import servlets.*;
import tests.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class Main {


    public static void main(String[] args) throws Exception {
        FilterHolder filterHolder = new FilterHolder(CrossOriginFilter.class);
        filterHolder.setInitParameter("allowedOrigins", "*");
        filterHolder.setInitParameter("allowedMethods", "GET, POST, DELETE");
        DBManager manager = new DBManager();

        /*
            Test input begin


        String pattern = "yyyy-MM-dd HH:mm:ss";
        String beginTimeString = "2021-03-16 22:00:00";
        String endTimeString = "2021-04-18 22:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime beginObservation = LocalDateTime.from(formatter.parse(beginTimeString));
        LocalDateTime endObservation = LocalDateTime.from(formatter.parse(endTimeString));

        RawDataAdapter RDAdapter = new RawDataAdapter(manager);
        RDAdapter.getUserTableJson("Goose", 4, beginObservation, endObservation);


         *   Test input end
         * */

        SessionControl control = new SessionControl(manager);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignUpRequestServlet(control)), "/auth/signup");
        context.addServlet(new ServletHolder(new LogInRequestServlet(control)), "/auth/login");
        context.addServlet(new ServletHolder(new AuthorizationRequestServlet(control)), "/auth/me");
        context.addServlet(new ServletHolder(new GroupMainInfoRequestServlet(control)), "/groups/getMainInfo/*");
        context.addServlet(new ServletHolder(new GroupsInformationRequestServlet(control)), "/groups/getGroupList");
        context.addServlet(new ServletHolder(new GroupCreationRequestServlet(control)),"/groups/createGroup/*");
        context.addServlet(new ServletHolder(new TableInformationRequestServlet(control)),"/groups/userTable/*");
        context.addFilter(filterHolder, "/*", null);
        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        Logger.getGlobal().info("Server started");
        server.join();
    }

}
