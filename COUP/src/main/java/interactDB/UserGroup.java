package interactDB;

import java.time.LocalDateTime;

public class UserGroup
{
    private int groupId;
    private String groupName;
    private LocalDateTime creationDate;
    private int groupStatus;
    private String groupDescription;

    public UserGroup(int groupId, String groupName, LocalDateTime creationDate, int groupStatus, String groupDescription)
    {
        this.groupId = groupId;
        this.groupName = groupName;
        this.creationDate = creationDate;
        this.groupStatus = groupStatus;
        this.groupDescription = groupDescription;
    }

    public int getGroupId()
    {
        return groupId;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public LocalDateTime getCreationDate()
    {
        return creationDate;
    }

    public int getGroupStatus()
    {
        return groupStatus;
    }

    public String getGroupDescription()
    {
        return groupDescription;
    }

    public String toString()
    {
        String retVal = "UserGroup ";
        retVal += groupId + ": " + groupName + ".\n";
        retVal += "Created: " + creationDate.toString() + ", Status: " + groupStatus + ".\n";
        retVal += "About: " + groupDescription;
        return retVal;
    }
}
