package servlets;

import accounts.SessionControl;
import com.google.gson.Gson;
import interactDB.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class GroupsInformationRequestServlet extends HttpServlet
{

    private SessionControl control = null;

    public GroupsInformationRequestServlet(SessionControl control)
    {
        this.control = control;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("post groups");
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {

        String reportReq = "doGet|GroupsInfo|";
        String sessionID = request.getSession().getId();
        reportReq += sessionID;
        if (control.authorization_check(sessionID) == null)
        {
            reportReq += "|not_authorized";
            System.out.println(reportReq);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Gson gson = new Gson();
            String groupNamesJson = null;
            try
            {
                //TODO - return json of following format:
                /*
                groups: [
                    {
                        name: "GooseTheFirst",
                        privilege: 1,
                        userCount: 177013,
                        creationDate: new Date()
                    },
                    {
                        name: "GooseTheSecond",
                        privilege: 2,
                        userCount: 1234,
                        creationDate: new Date()
                    },
                    {
                        name: "GooseTheThird",
                        privilege: 0,
                        userCount: 1243,
                        creationDate: new Date()
                    },
                    {
                        name: "GooseTheForth",
                        privilege: 1,
                        userCount: 324,
                        creationDate: new Date()
                    },
                    {
                        name: "GooseTheFive",
                        privilege: 2,
                        userCount: 123,
                        creationDate: new Date()
                    }
                ]
                 */
                groupNamesJson = gson.toJson(control.getManager().getGroupNamesByUserId(control.authorization_check(sessionID)));
            } catch (SQLException e)
            {
                throw new ServletException("Could not return group names - internal server error");
            }

            System.out.println(groupNamesJson);
            response.getWriter().println(groupNamesJson);

            return;
        }
        reportReq += "|success";
        System.out.println(reportReq);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doOptions(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("doOpt groups");
    }
}
