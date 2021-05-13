package accounts;

import dataProcessing.RawDataAdapter;
import interactDB.DBManager;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;

public class SessionControl
{
    private DBManager manager;
    private RawDataAdapter rawDataAdapter;
    private HashMap<String, Integer> sessions;

    public SessionControl(DBManager manager)
    {
        this.manager = manager;
        rawDataAdapter = new RawDataAdapter(manager);
        sessions = new HashMap<String, Integer>();
    }

    public DBManager getManager()
    {
        return manager;
    }

    public RawDataAdapter getRawDataAdapter()
    {
        return rawDataAdapter;
    }

    public Integer authorization_check(String sessionID) //return login if exist in sessions
    {
        return sessions.get(sessionID);
    }

    public boolean logIn(String login, String password, String sessionID) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        if (manager.isUserValid(login, password))
        {
            sessions.put(sessionID, manager.getUserIdByLogin(login));
            return true;
        }
        return false;
    }

    public boolean registerUser(String login, String password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        return manager.registerUser(login, password);

        //Регистрация

        /*if(users.containsKey(login)) return false;
        users.put(login,password);
        return true;*/
    }

    public Integer logOut(String sessionID)
    {
        return sessions.remove(sessionID);
    }


}
