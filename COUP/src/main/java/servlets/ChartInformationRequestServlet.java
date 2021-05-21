package servlets;

import accounts.SessionControl;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import requestObjects.RequestUserObject;
import templater.RequestMapGenerator;
import templater.RequestUserObjectDeserializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ChartInformationRequestServlet extends HttpServlet {
    private SessionControl control = null;
    private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
    GsonBuilder builder = new GsonBuilder();
    public ChartInformationRequestServlet(SessionControl control)
    {
        this.control = control;
        builder.registerTypeAdapter(RequestUserObject.class, new RequestUserObjectDeserializer());
    }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        String reportReq = "doGet|TableInformation|";
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
        //request.getPathInfo(); //???
        String requestJson = request.getReader().readLine().toString();

        RequestUserObject user = null;
        try {
            user = builder.create().fromJson(requestJson, RequestUserObject.class);
        }
        catch (JsonParseException | NullPointerException e )
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("BAD_JSON");
        }

        String responseJson = null;
        // FIXME - Запись респонза

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(responseJson);
    }

}
