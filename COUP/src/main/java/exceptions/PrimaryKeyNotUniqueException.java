package exceptions;

public class PrimaryKeyNotUniqueException extends Exception
{
    public PrimaryKeyNotUniqueException(String tableName, String PKField)
    {
        super("There are multiple primary keys in " + tableName + ". PK field:" + PKField);
    }
}
