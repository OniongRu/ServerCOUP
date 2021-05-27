package dataProcessing;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import interactDB.*;
import exceptions.PrimaryKeyNotUniqueException;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RawDataAdapter
{
    DBManager manager;

    RawDataAdapter()
    {
        manager = new DBManager();
    }

    public RawDataAdapter(DBManager manager)
    {
        this.manager = manager;
    }

    /*groupID: int
        groupName: string
        description: string
        userList: [{login: string, role: int},{...}]
        programList: [{name:string},{...}]
    //If there is no group with such id returns null*/
    public FrontPageInfoClass getFrontPageInfo(int groupId) throws PrimaryKeyNotUniqueException, SQLException
    {
        String groupName = null;
        String groupDescription = null;
        try
        {
            var arrayList = manager.getGroupHeaderById(groupId);
            if (arrayList == null)
            {
                return null;
            }

            groupName = arrayList[0];
            groupDescription = arrayList[1];
        }
        catch (SQLException | PrimaryKeyNotUniqueException e)
        {
            System.out.println(e);
            return null;
        }

        Set<UserData> userSet = new HashSet<>();
        try
        {
            userSet = manager.getUserDataByGroupId(groupId);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }

        Set<String> programNamesSet = new HashSet<>();
        if (userSet.size() == 0)
        {
            return new FrontPageInfoClass(groupId, groupName, groupDescription, userSet, programNamesSet);
        }

        try
        {
            programNamesSet = manager.getAllProgramNamesByUserId(userSet.stream().map((element)->element.getId()));
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return new FrontPageInfoClass(groupId, groupName, groupDescription, userSet, programNamesSet);
    }

    public enum UserRole
    {
        //Can invite and kick users in group, view their activities
        Administrator,

        //Can view users' activities
        Analyst,

        //Can provide information about their computers to people above, view own information
        Contributor
    }

    public static UserRole castIntToRole(Integer role)
    {
        if (role == null)
            return null;

        switch (role)
        {
            case 1:
            {
                return UserRole.Administrator;
            }
            case 2:
            {
                return UserRole.Analyst;
            }
            case 3:
            {
                return UserRole.Contributor;
            }
            default:
            {
                return null;
            }
        }
    }

    public static Integer castRoleToInt(UserRole role)
    {
        if (role == null)
        {
            return null;
        }

        switch (role)
        {
            case Administrator:
            {
                return 1;
            }
            case Analyst:
            {
                return 2;
            }
            case Contributor:
            {
                return 3;
            }
            default:
            {
                return null;
            }
        }
    }

    private static class UnionClass
    {
        @SerializedName(value = "Columns")
        private ArrayList<?> columnGroupDescriptors;
        @SerializedName(value = "Data")
        private ArrayList<UserActivityInfo> userActivityInfo;

        public UnionClass(ArrayList<ColumnGroupDescriptor> columnGroupDescriptors, ArrayList<UserActivityInfo> userActivityInfo)
        {
            this.columnGroupDescriptors = columnGroupDescriptors;
            this.userActivityInfo = userActivityInfo;
        }
    }

    private static class ColumnDescriptor
    {
        @SerializedName(value = "Header")
        public String header;
        @SerializedName(value = "accessor")
        public String accessor;

        ColumnDescriptor(String header, int columnGroupIndex)
        {
            this.header = header;
            accessor = String.valueOf(columnGroupIndex);
        }

        public ColumnDescriptor(String header, String accessor)
        {
            this.header = header;
            this.accessor = accessor;
        }
    }

    private static class ColumnGroupDescriptor
    {
        public static int counter = 1;

        @SerializedName(value = "Header")
        public String header;

        @SerializedName(value = "columns")
        public ArrayList<ColumnDescriptor> columns;

        ColumnGroupDescriptor(LocalDateTime columnDate, int timeUnit)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            header = columnDate.format(formatter) + " - " + columnDate.plusHours(timeUnit).format(formatter);
            columns = new ArrayList<>();
            columns.add(new ColumnDescriptor("CPU usage", counter + "c"));
            columns.add(new ColumnDescriptor("RAM usage", counter + "r"));
            columns.add(new ColumnDescriptor("Foreground time", counter + "f"));
            columns.add(new ColumnDescriptor("Total time", counter + "t"));
            columns.add(new ColumnDescriptor("Threads amount", counter + "th"));
            columns.add(new ColumnDescriptor("Measures count", counter + "m"));
            counter = counter + 1;
        }
    }

    public String getUserTableJson(int userId, ArrayList<String> programs, int timeUnit, LocalDateTime startDate, LocalDateTime endDate)
    {
        Clock clock = Clock.systemDefaultZone();
        Instant start = clock.instant();
        ArrayList<UserActivityInfo> userActivityInfo = getUserTableInfo(userId, programs, timeUnit, startDate, endDate);
        Instant end = clock.instant();
        System.out.println(start);
        System.out.println(end);
        ArrayList columnGroups = new ArrayList<>();
        //map (date,counter)
        //userActivityInfo(
            //program-> generate row with program  str = program:"program"
            // pr= userActivityInfo(program)
            // get counter by pr->date
            //add cells with accessor
        // )
        columnGroups.add(new ColumnDescriptor("Programs", "program"));
        ColumnGroupDescriptor.counter = 1;
        LocalDateTime currentHeaderGroupDate = null;
        Map<LocalDateTime, Integer> dateCounterMap = new HashMap<>();
        for (int i = 0; i < userActivityInfo.size(); i++)
        {
            LocalDateTime currentCreationDate = userActivityInfo.get(i).getCreationDate();
            if (!currentCreationDate.equals(currentHeaderGroupDate))
            {
                dateCounterMap.put(currentCreationDate, ColumnGroupDescriptor.counter);
                columnGroups.add(new ColumnGroupDescriptor(currentCreationDate, timeUnit));
                currentHeaderGroupDate = currentCreationDate;
            }
        }

        userActivityInfo.sort(Comparator.comparing(UserActivityInfo::getName));

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>()
        {
            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonDeserializationContext) throws JsonParseException
            {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss, dd.MM.yyyy");
                return new JsonPrimitive(dateTimeFormatter.format(localDateTime));
            }
        });

        Type collectionType = new TypeToken<ArrayList<UserActivityInfo>>() {}.getType();

        gsonBuilder.registerTypeAdapter(new TypeToken<ArrayList<UserActivityInfo>>() {}.getType(), new JsonSerializer<ArrayList<UserActivityInfo>>()
        {
            @Override
            public JsonElement serialize(ArrayList<UserActivityInfo> userActivityInfos, Type type, JsonSerializationContext jsonSerializationContext)
            {
                JsonArray programsArray = new JsonArray();

                String currentProgram = "";
                JsonObject program = null;

                for (int i = 0; i < userActivityInfos.size(); i++)
                {
                    if (!currentProgram.equals(userActivityInfos.get(i).getName()))
                    {
                        if (!currentProgram.equals(""))
                        {
                            programsArray.add(program);
                        }
                        currentProgram = userActivityInfos.get(i).getName();
                        program = new JsonObject();
                        program.addProperty("program", userActivityInfos.get(i).getName());
                    }

                    int index = dateCounterMap.get(userActivityInfos.get(i).getCreationDate());
                    program.addProperty(index + "c", userActivityInfos.get(i).getCpuUsage());
                    program.addProperty(index + "r", userActivityInfos.get(i).getRamUsage());
                    program.addProperty(index + "f", userActivityInfos.get(i).getActiveWindowTime());
                    program.addProperty(index + "t", userActivityInfos.get(i).getWindowTime());
                    program.addProperty(index + "th", userActivityInfos.get(i).getThreadAmount());
                    program.addProperty(index + "m", userActivityInfos.get(i).getPackagesAmount());
                }

                programsArray.add(program);

                return programsArray;
            }
        });

        Gson gson = gsonBuilder.create();

        Type type = new TypeToken<ArrayList<UserActivityInfo>>(){}.getType();
        //String str = gson.toJson(userActivityInfo);
        //String str = gson.toJson(userActivityInfo, type);
        String str = gson.toJson(new UnionClass(columnGroups, userActivityInfo), UnionClass.class);
        System.out.println(str);
        return str;
    }

    public ArrayList<UserActivityInfo> getUserTableInfo(String userName, ArrayList<String> programs, int timeUnit, LocalDateTime startDate, LocalDateTime endDate)
    {
        try
        {
            return getUserTableInfo(manager.getUserIdByLogin(userName), programs, timeUnit, startDate, endDate);
        } catch (SQLException e)
        {
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<UserActivityInfo> getUserTableInfo(int userId, ArrayList<String> programs, int timeScale, LocalDateTime startDate, LocalDateTime endDate)
    {
        ArrayList<UserActivityInfo> dividedActivityInfo = new ArrayList<>();

        LocalDateTime scaledTimeStart = startDate;
        LocalDateTime scaledTimeEnd = startDate.plusHours(timeScale);
        ArrayList<UserActivityInfo> activityInfoInTimeScale = null;
        try
        {
            /*if (programs.size() == 0)
            {*/
                activityInfoInTimeScale = manager.getUserActivity(userId, timeScale, startDate, endDate);
            /*}
            else
            {
                activityInfoInTimeScale = manager.getUserActivity(userId, programs, timeScale, startDate, endDate);
            }*/
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        ListIterator<UserActivityInfo> activityInfoListIterator = activityInfoInTimeScale.listIterator();


        String name = null;
        double cpuUsage = 0;
        BigInteger ramUsage = new BigInteger("0");
        int threadAmount = 0;
        int activeWindowTime = 0;
        int windowTime = 0;
        int packagesAmount = 0;
        LocalDateTime creationTime = null;

        UserActivityInfo hourInfo = null;
        int hoursProcessed = 0;

        while (activityInfoListIterator.hasNext())
        {
            hourInfo = activityInfoListIterator.next();

            if (name == null)
            {
                name = hourInfo.getName();
                creationTime = hourInfo.getCreationDate();
            }

            if(!hourInfo.getName().equals(name) || hourInfo.getCreationDate().isAfter(scaledTimeEnd))
            {
                if (hoursProcessed != 0)
                {
                    dividedActivityInfo.add(new UserActivityInfo(
                            name,
                            creationTime,
                            (cpuUsage / hoursProcessed),
                            ramUsage.divide(BigInteger.valueOf(hoursProcessed)).longValue(),
                            (threadAmount / hoursProcessed),
                            activeWindowTime,
                            windowTime,
                            packagesAmount
                    ));
                }

                name = hourInfo.getName();
                cpuUsage = 0;
                ramUsage = new BigInteger("0");
                threadAmount = 0;
                activeWindowTime = 0;
                windowTime = 0;
                packagesAmount = 0;
                creationTime = hourInfo.getCreationDate();
                hoursProcessed = 0;

                if (hourInfo.getCreationDate().isAfter(scaledTimeEnd) || hourInfo.getCreationDate().isEqual(scaledTimeEnd))
                {
                    activityInfoListIterator.previous();
                    scaledTimeStart = scaledTimeEnd;
                    scaledTimeEnd = scaledTimeEnd.plusHours(timeScale);
                    continue;
                }
            }

            cpuUsage += hourInfo.getCpuUsage();
            ramUsage = ramUsage.add(BigInteger.valueOf(hourInfo.getRamUsage()));
            threadAmount += hourInfo.getThreadAmount();
            activeWindowTime += hourInfo.getActiveWindowTime();
            windowTime += hourInfo.getWindowTime();
            packagesAmount += hourInfo.getPackagesAmount();
            hoursProcessed++;
        }
        dividedActivityInfo.add(new UserActivityInfo(
                name,
                creationTime,
                (cpuUsage / hoursProcessed),
                ramUsage.divide(BigInteger.valueOf(hoursProcessed)).longValue(),
                (threadAmount / hoursProcessed),
                activeWindowTime,
                windowTime,
                packagesAmount
        ));

        return dividedActivityInfo;
    }
}
