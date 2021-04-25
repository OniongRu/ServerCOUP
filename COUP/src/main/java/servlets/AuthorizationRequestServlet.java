package servlets;

import accounts.ReplacmentDB;
import accounts.SessionControl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationRequestServlet extends HttpServlet {

    private SessionControl control = new SessionControl();

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
        System.out.println("get auth");
                //TODO
    }

    public void doOptions(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doOpt auth");
    }

}
