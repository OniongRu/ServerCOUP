package dataProcessing;

public class Properties
{
    static Properties instance;
    static String connectionString;
    static String password;

    Properties()
    {
        if (instance == null)
        {
            instance = this;
        }
    }

    Properties getInstance()
    {
        return this;
    }

    public static String getConnectionString()
    {
        return connectionString;
    }

    public static String getPassword()
    {
        return password;
    }
}
