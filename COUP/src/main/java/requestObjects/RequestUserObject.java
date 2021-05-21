package requestObjects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RequestUserObject {
    private int groupID;
    private String user;
    private int timeScale;
    private Date interval_date_start;
    private Date interval_date_end;
    private String title;
    private ArrayList<String> programs;
    private SimpleDateFormat formatter = new SimpleDateFormat("E MMMM dd yyyy HH:mm:ss", Locale.ENGLISH); //Tue May 11 2021 00:00:00 GMT+0300 (Москва, стандартное время)
    public RequestUserObject(int groupID, String user, int timeScale, Date start_time, Date end_time, ArrayList<String> programs, String title)
    {
        this.groupID = groupID;
        this.user = user;
        this.timeScale = timeScale;
        this.interval_date_start = start_time;
        this.interval_date_end = end_time;
        this.programs=programs;
        this.title = title;
    }
    public void show()
    {
        System.out.println("---------UserTest---------");
        System.out.println(groupID);
        System.out.println(user);
        System.out.println(timeScale);
        System.out.println(formatter.format(interval_date_start));
        System.out.println(formatter.format(interval_date_end));
        System.out.println(programs.toString());
        System.out.println(title);
    }

    public int getGroupID() {
        return groupID;
    }

    public String getUser() {
        return user;
    }

    public int getTimeScale() {
        return timeScale;
    }

    public Date getInterval_date_start() {
        return interval_date_start;
    }

    public Date getInterval_date_end() {
        return interval_date_end;
    }

    public ArrayList<String> getPrograms() {
        return programs;
    }

    public String getTitle() {
        return title;
    }


}
