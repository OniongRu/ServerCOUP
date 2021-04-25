package accounts;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ReplacmentDB {

    private  ArrayList <Group> groups;
    private String groupsJson;
    private int numberGroups=3;
    public ReplacmentDB(int numberGroups)
    {
        this.numberGroups = numberGroups;
        ArrayList<String> users = new ArrayList<String>(Arrays.asList("Leonid", "Mihail","Lepeha","Goose","Goosak","Goosina","MemAdept"));
        ArrayList<String> programs = new ArrayList<String>(Arrays.asList("Skype", "Google","Yandex","Doka2","untitledGooseGame","Game","notGame"));
        groups = new ArrayList<>();
        int groupID;
        String groupName=new String();
        String description=new String();
        for(int i=0;i<numberGroups;i++)
        {
            groupID=i;
            groupName="group"+Integer.toString(i);
            description="description"+Integer.toString(i);
            groups.add(new Group(groupID,groupName,description,users,programs));
        }
        Gson gson = new Gson();
        groupsJson = gson.toJson(groups);
    }
    public String getGroupsJson()
    {
        return groupsJson;
    }
    public String getCertainGroupJson(int groupID)
    {
        if(numberGroups<=groupID) return null;
        Gson gson = new Gson();
        return gson.toJson(groups.get(groupID));
    }
}
