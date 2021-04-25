package accounts;

import java.util.HashMap;

public class SessionControl {
    private HashMap<String,String> users;
    private HashMap<String,String> sessions;

    public SessionControl()
    {
        users = new HashMap<String,String>();
        sessions = new HashMap<String,String>();
    }

    public String authorization_check(String sessionID) //return login if exist in sessions
    {
        return sessions.get(sessionID);
    }

    public boolean logIn(String login, String password, String sessionID)
    {
        String password_by_login = users.get(login);
        if(password_by_login!=null) {
            if (password_by_login.equals(password))
            {
                sessions.put(sessionID, login);
                return true;
            }
        }
        return false;
    }

    public boolean registerUser(String login, String password)
    {
        if(users.containsKey(login)) return false;
        users.put(login,password);
        return true;
    }

    public String logOut(String sessionID)
    {
        return sessions.remove(sessionID);
    }


}
