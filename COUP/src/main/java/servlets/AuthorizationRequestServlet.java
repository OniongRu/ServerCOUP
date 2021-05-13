package servlets;

import accounts.SessionControl;
import exceptions.PrimaryKeyNotUniqueException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AuthorizationRequestServlet extends HttpServlet {

    private SessionControl control = null;

    public AuthorizationRequestServlet(SessionControl control)
    {
        this.control = control;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        System.out.println("post auth");
    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Goose!");
        try
        {
            response.getWriter().println(control.getManager().getUserNameDyId(control.authorization_check(request.getSession().getId())));
        }
        catch (SQLException | PrimaryKeyNotUniqueException e)
        {
            throw new ServletException("Could not check authorization - internal server error");
        }

        System.out.println("get auth");
                //TODO
    }

    public void doOptions(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doOpt auth");
    }

}
