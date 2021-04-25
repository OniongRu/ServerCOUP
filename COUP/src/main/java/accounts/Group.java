package accounts;

import java.util.ArrayList;

public class Group {
    private int groupID;
    private String groupName;
    private String description;
    private ArrayList<String>userList;
    private ArrayList<String>programList;

    public Group(int groupID, String groupName, String description, ArrayList<String> userList, ArrayList<String> programList)
    {
        this.userList = new ArrayList<>();
        this.programList = new ArrayList<>();
        this.groupID=groupID;
        this.groupName=groupName;
        this.description=description;
        this.userList.addAll(userList);
        this.programList.addAll(programList);
    }

}
