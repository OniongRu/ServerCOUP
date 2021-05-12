package interactDB;

import java.util.Set;

public class FrontPageInfoClass
{
    private int groupId;
    private String groupName;
    private String description;
    private Set<UserData> userList;
    private Set<String> programList;

    public FrontPageInfoClass(int groupId, String groupName, String groupDescription, Set<UserData> userList, Set<String> programList)
    {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = groupDescription;
        this.userList = userList;
        this.programList = programList;
    }

    public String toString()
    {
        StringBuilder retVal = new StringBuilder("Front page info class.\n" +
                "GroupId: " + groupId + ", name: " + groupName + "\n" +
                "Description: " + description + "\nParticipants:\n");

        for (var userInfo : userList)
        {
            retVal.append(userInfo.toString()).append("\n");
        }

        retVal.append("Total programs:\n");

        for (var program : programList)
        {
            retVal.append(program).append("\n");
        }

        return retVal.toString();
    }
}
