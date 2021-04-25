package servlets;

import accounts.ReplacmentDB;
import accounts.SessionControl;
import templater.RequestMapGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns={"/auth/signup/*"})
@MultipartConfig
public class SignUpRequestServlet extends HttpServlet {

    private SessionControl control = new SessionControl();

    public SignUpRequestServlet(SessionControl control)
    {
        this.control = control;
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String reportReq="doPost|SignUp|";
        String sessionID =request.getSession().getId();
        reportReq+=sessionID;
        if(control.authorization_check(sessionID)!=null)
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println(false);
            reportReq+="|Already_in_system";
            System.out.println(reportReq);
            return;
        }
        String requestJson = request.getReader().readLine().toString();
        //System.out.println(requestJson);
        Map <String,String> requestMap = RequestMapGenerator.entranceData(requestJson);
        String login = requestMap.get("login");
        String password = requestMap.get("password");
        if(login.isEmpty()||password.isEmpty())
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            response.getWriter().println(false);
            reportReq+="|Not_enough_data";
            System.out.println(reportReq);
            return;
        }
        else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        if(!control.registerUser(login,password))
        {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().println(false);
            reportReq+="|login_already_use";
            System.out.println(reportReq);
            return;
        }
        reportReq+="|new_user|"+login+"|_|"+password;
        System.out.println(reportReq);
        control.logIn(login,password,sessionID);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().println(true);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get up");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(true);
    }
    public void doOptions(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doOpt up");
    }

}
