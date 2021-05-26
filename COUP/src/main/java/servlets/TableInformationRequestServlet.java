package servlets;

import accounts.SessionControl;
import com.google.gson.*;
import requestObjects.RequestFilters;
import requestObjects.RequestUserObject;
import templater.RequestFiltersDeserializer;
import templater.RequestUserObjectDeserializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class TableInformationRequestServlet extends HttpServlet {
    private SessionControl control = null;
    GsonBuilder builderRequestUser = new GsonBuilder();
    GsonBuilder builderFilters = new GsonBuilder();

    public TableInformationRequestServlet(SessionControl control)
    {
        this.control = control;
        builderRequestUser.registerTypeAdapter(RequestUserObject.class, new RequestUserObjectDeserializer());
        builderFilters.registerTypeAdapter(RequestFilters.class, new RequestFiltersDeserializer());
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException
    {
        String reportReq = "doPost|TableInformation|";
        String sessionID = request.getSession().getId();
        reportReq += sessionID;

        if (control.authorization_check(sessionID) == null)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(false);
            reportReq += "|not_authorized";
            System.out.println(reportReq);
            return;
        }

        String responseJson = null;
        String requestJson = request.getReader().readLine().toString();
        RequestFilters filters = null;
        try {
            filters = builderFilters.create().fromJson(requestJson, RequestFilters.class);
        }
        catch (JsonParseException | NullPointerException e )
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("BAD_JSON_0");
            return;
        }
        switch (filters.getTitle())
        {
            case "user":
                RequestUserObject requestedDataDescriptor = null;
                try {
                    requestedDataDescriptor = builderRequestUser.create().fromJson(requestJson, RequestUserObject.class);
                }
                catch (JsonParseException | NullPointerException e )
                {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().println("BAD_JSON_F_USER");
                    return;
                }
                responseJson = control.getRawDataAdapter().getUserTableJson(requestedDataDescriptor.getUser(), requestedDataDescriptor.getTimeScale(), requestedDataDescriptor.getIntervalDateStart(), requestedDataDescriptor.getIntervalDateEnd());
                // FIXME - Запись респонза
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("BAD_FILTER_NAME");
                return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(responseJson);
    }
}
