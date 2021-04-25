package servlets;

import accounts.ReplacmentDB;
import accounts.SessionControl;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupsInformationRequestServlet extends HttpServlet {

    private SessionControl control = new SessionControl();
    private ReplacmentDB groupsDB;
    public GroupsInformationRequestServlet(SessionControl control)
    {
        this.control = control;
        groupsDB = new ReplacmentDB(5);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        System.out.println("post groups");
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String reportReq="doGet|GroupsInfo|";
        String sessionID=request.getSession().getId();
        reportReq+=sessionID;
        if(control.authorization_check(sessionID)==null)
        {
            reportReq+="|not_authorized";
            System.out.println(reportReq);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(false);
            return;
        }
        reportReq+="|success";
        System.out.println(reportReq);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(groupsDB.getCertainGroupJson(1));
    }

    public void doOptions(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doOpt groups");
    }
}
