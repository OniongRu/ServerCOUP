package servlets;

import accounts.SessionControl;
import templater.RequestMapGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class GroupCreationRequestServlet extends HttpServlet {

    private SessionControl control = null;

    public GroupCreationRequestServlet(SessionControl control){this.control = control;}

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String reportReq = "doPost|GroupCreation|";
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
        String requestJson = request.getReader().readLine().toString(); //json{name:"goose",privacy:1}
        Map<String, String> requestMap = RequestMapGenerator.entranceData(requestJson);
        String group_name = requestMap.get("name");
        String group_privacy = requestMap.get("privacy");

        //TODO - допилить создание группы в БД
        reportReq+="group_successfully_created|name:"+group_name+"|privacy:"+group_privacy; //TODO - методы для получения даты создания группы и тп
        System.out.println(reportReq);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().println(true);

    }
    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get group creation");
    }
}
