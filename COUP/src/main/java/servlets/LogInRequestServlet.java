package servlets;

import accounts.SessionControl;
import templater.RequestMapGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Map;


@WebServlet(urlPatterns = {"/auth/signup/*"})
@MultipartConfig
public class LogInRequestServlet extends HttpServlet
{

    private SessionControl control = null;

    public LogInRequestServlet(SessionControl control)
    {
        this.control = control;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException
    {
        String reportReq = "doPost|LogIn|";
        String sessionID = request.getSession().getId();
        reportReq += sessionID;
        if (control.authorization_check(sessionID) != null)
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println(false);
            reportReq += "|Already_in_system";
            System.out.println(reportReq);
            return;
        }
        String requestJson = request.getReader().readLine().toString();
        Map<String, String> requestMap = RequestMapGenerator.entranceData(requestJson);
        String login = requestMap.get("login");
        String password = requestMap.get("password");
        if (login.isEmpty() || password.isEmpty())
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            response.getWriter().println(false);
            reportReq += "|Not_enough_data";
            System.out.println(reportReq);
            return;
        } else
        {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        try
        {
            if (!control.logIn(login, password, sessionID))
            {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().println(false);
                reportReq += "|bad_data";
                System.out.println(reportReq);
                return;
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            throw new ServletException("Login failed - internal server error");
        }

        reportReq += "|login_success|" + login + "|_|" + password;
        ;
        System.out.println(reportReq);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().println(true);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("get in");
    }

    public void doOptions(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("doOpt in");
    }


    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        String reportReq = "doDelete|LogIn|";
        String sessionID = request.getSession().getId();
        reportReq += sessionID;
        if (control.authorization_check(sessionID) == null)
        {
            reportReq += "|already_not_in_system";
            System.out.println(reportReq);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println(false);
            return;
        }
        reportReq += "|log_out|" + control.logOut(sessionID);
        System.out.println(reportReq);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(true);
        return;
    }
}
