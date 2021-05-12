package servlets;

import accounts.SessionControl;
import com.google.gson.Gson;
import exceptions.PrimaryKeyNotUniqueException;
import interactDB.FrontPageInfoClass;
import templater.RequestMapGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class GroupMainInfoServlet extends HttpServlet
{
    private SessionControl control = null;

    public GroupMainInfoServlet(SessionControl control)
    {
        this.control = control;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String reportReq = "doGet|GroupMainInfo|";
        String sessionID = request.getSession().getId();
        reportReq += sessionID;
        if(control.authorization_check(sessionID) == null)
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println(false);
            reportReq += "|Not_authorized";
            System.out.println(reportReq);
            return;
        }

        String id = request.getParameter("id");
        FrontPageInfoClass frontPageInfo = null;
        try
        {
            frontPageInfo = control.getRawDataAdapter().getFrontPageInfo(Integer.valueOf(id));
        }
        catch(SQLException | PrimaryKeyNotUniqueException e)
        {
            throw new ServletException("Could not get front page info - internal server error");
        }

        Gson gson = new Gson();
        String frontPageInfoJson = gson.toJson(frontPageInfo);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(frontPageInfoJson);
        reportReq += "|Success";
        System.out.println(reportReq);
    }
}
