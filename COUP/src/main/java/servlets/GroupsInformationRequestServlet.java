package servlets;

import accounts.SessionControl;
import com.google.gson.*;
import interactDB.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
            return;
        }
        reportReq += "|success";
        System.out.println(reportReq);

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>()
        {
            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonDeserializationContext) throws JsonParseException
            {
                /*DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss, dd.MM.yyyy");
                return new JsonPrimitive(dateTimeFormatter.format(localDateTime));*/
                return new JsonPrimitive(localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond());
            }
        });

        Gson gson = gsonBuilder.create();
        String groupNamesJson = null;
        try
        {
            //TODO - return json of following format:
                /*
                groups: [
                    {
                        id: 1,
                        name: "GooseTheFirst",
                        privilege: 1,
                        userCount: 177013,
                        creationDate: new Date()
                    },
                    {
                        id: 2,
                        name: "GooseTheSecond",
                        privilege: 2,
                        userCount: 1234,
                        creationDate: new Date()
                    },
                    {
                        id: 3,
                        name: "GooseTheThird",
                        privilege: 0,
                        userCount: 1243,
                        creationDate: new Date()
                    },
                    {
                        id: 4,
                        name: "GooseTheForth",
                        privilege: 1,
                        userCount: 324,
                        creationDate: new Date()
                    },
                    {
                        id: 5,
                        name: "GooseTheFive",
                        privilege: 2,
                        userCount: 123,
                        creationDate: new Date()
                    }
                ]
                 */
        groupNamesJson = gson.toJson(control.getManager().getUserGroupDescriptors(control.authorization_check(sessionID)));
        } catch (SQLException e)
        {
            throw new ServletException("Could not return group names - internal server error");
        }

        System.out.println(groupNamesJson);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(groupNamesJson);
    }

    public void doOptions(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("doOpt groups");
    }
}
