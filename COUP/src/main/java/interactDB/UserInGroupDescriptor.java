package interactDB;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class UserInGroupDescriptor
{
    int id;
    @SerializedName(value = "name")
    String groupName;
    int privilege;
    int userCount;
    @SerializedName(value = "creationDate")
    LocalDateTime groupCreationDate;

    public UserInGroupDescriptor(int id, String groupName, int privilege, int userCount, LocalDateTime groupCreationDate)
    {
        this.id = id;
        this.groupName = groupName;
        this.privilege = privilege;
        this.userCount = userCount;
        this.groupCreationDate = groupCreationDate;
    }

    public int getId()
    {
        return id;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public int getPrivilege()
    {
        return privilege;
    }

    public int getUserCount()
    {
        return userCount;
    }

    public LocalDateTime getGroupCreationDate()
    {
        return groupCreationDate;
    }
}