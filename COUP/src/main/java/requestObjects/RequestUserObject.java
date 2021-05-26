package requestObjects;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RequestUserObject
{
    private int groupID;
    private int user;
    private int timeScale;
    private LocalDateTime intervalDateStart;
    private LocalDateTime intervalDateEnd;
    private String title;
    private ArrayList<String> programs;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMMM dd yyyy HH:mm:ss", Locale.ENGLISH); //Tue May 11 2021 00:00:00 GMT+0300
    //private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMMM dd yyyy HH:mm:ss O", Locale.ENGLISH); //Tue May 11 2021 00:00:00 GMT+0300
    //private SimpleDateFormat formatter = new SimpleDateFormat("E MMMM dd yyyy HH:mm:ss", Locale.ENGLISH); //Tue May 11 2021 00:00:00 GMT+0300 (Москва, стандартное время)

    public RequestUserObject(int groupID, int user, int timeScale, LocalDateTime startTime, LocalDateTime endTime, ArrayList<String> programs, String title)
    {
        this.groupID = groupID;
        this.user = user;
        this.timeScale = timeScale;
        this.intervalDateStart = startTime;
        this.intervalDateEnd = endTime;
        this.programs = programs;
        this.title = title;
    }

    public void show()
    {
        System.out.println("---------UserTest---------");
        System.out.println(groupID);
        System.out.println(user);
        System.out.println(timeScale);
        System.out.println(intervalDateStart.format(formatter));
        System.out.println(intervalDateEnd.format(formatter));
        System.out.println(programs.toString());
        System.out.println(title);
    }

    public int getGroupID()
    {
        return groupID;
    }

    public int getUser()
    {
        return user;
    }

    public int getTimeScale()
    {
        return timeScale;
    }

    public LocalDateTime getIntervalDateStart()
    {
        return intervalDateStart;
    }

    public LocalDateTime getIntervalDateEnd()
    {
        return intervalDateEnd;
    }

    public ArrayList<String> getPrograms()
    {
        return programs;
    }

    public String getTitle()
    {
        return title;
    }


}
