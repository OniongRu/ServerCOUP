package interactDB;

import com.google.gson.annotations.Expose;

public class UserData
{
    @Expose(deserialize = false, serialize = false)
    private int id;
    private String login;
    private int role;

    public UserData(int id, String login, int role)
    {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    public String toString()
    {
        return "Login: " + login + ", role: " + role;
    }

    public int getId()
    {
        return id;
    }
}
