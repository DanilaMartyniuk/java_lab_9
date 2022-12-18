package server;

import java.util.*;
public class RemoteHandler implements Handler{
    public static List<User> users = new ArrayList<User>();
    public RemoteHandler() {
    }


    public int getCurrentUserId (String username)
    {
        int i = 0;
        for(; i < users.size(); i ++)
        {
            if (username.equals(users.get(i).getName())) 
            {
                break;
            }
        }
        return i;
    }
    public void setTime (String day, int time, int user)
    {
        int iDay = convertDay(day) - 1;
        users.get(user).setTime(iDay, time - 1);
    }

    public void appendPending (String resp, int user2, int user)
    {
        users.get(user2 - 1).apendPending(resp,user);
    }

    public String createMeet(int user2, int user)
    {
        String ans = User.matchTimes(users.get(user), users.get(user2 - 1));
        return ans;
    }

    public void connectUser(String username)
    {
        Boolean exists = false;
        for(int i = 0; i < users.size(); i++)
        {
            String debug = users.get(i).getName();
            if(debug.equals(username))
            {
                System.out.println("logged in");
                exists = true;
                break;
            }
        }
            if (!exists){
                users.add(new User(username));
                System.out.print("Created new user");
            }
    }

    public void approove(int approoveId, int user)
    {
        int time, day;
        approoveId -= 1;
        int user2 = users.get(user).pendingId.get(approoveId);
        day = (users.get(user).pending.get(approoveId).charAt(5)) - '0' - 1;
        time = (users.get(user).pending.get(approoveId).charAt(13)) - '0' - 1;
        users.get(user).setTime(day, time);
        users.get(user2).setTime(day, time);
        users.get(user).pending.remove(approoveId);
        users.get(user).pendingId.remove(approoveId);
    }


    public String showUsers()
    {
        String output = "";
        for (int i = 0; i < users.size(); i++)
        {
            output += ((i+1)+". " + users.get(i).getName()+ "\n");
        }
        return output;
    }

    public int convertDay(String day)
    {
        int iDay = 0;
        switch(day)
        {
            case ("mon"):
            {
                iDay = 1;
                break;
            }
            case ("tue"):
            {
                iDay = 2;
                break;
            }
            case ("wen"):
            {
                iDay = 3;
                break;
            }
            case ("thu"):
            {
                iDay = 4;
                break;
            }
            case ("fri"):
            {
                iDay = 5;
                break;
            }
            case ("sun"):
            {
                iDay = 6;
                break;
            }
            case ("sat"):
            {
                iDay = 7;
                break;
            }
        }
        return iDay;
    }

    public String showPending(int user)
    {
        String resp = "";
        System.out.println(users.get(user).pending.size());
        System.out.println(user);
        for (int i = 0; i < users.get(user).pending.size(); i++)
        {
            resp += (i + 1) + ". When: " + users.get(user).pending.get(i) + " From: " + users.get(users.get(user).pendingId.get(i)).getName() + "\n" ;
        } 
        if(users.get(user).pending.size() == 0)
        {
            resp += "0.No pending requests";
        }
        return resp;
    }

    public String showSchedule(int user)
    {
        String schedule = "        mon tue wen thu fri sun sat\n";
        Boolean[][] timetable = users.get(user).getTimetable();
        for(int i = 0; i < 4; i++)
        {
            schedule += ((i+1) + ". " + (10 + i * 2) + "-" + (12 + i * 2) + " ");
            for(int j = 0; j < 7; j ++)
            {
                if(timetable[j][i])
                {
                    schedule += (1 + "   ");
                }
                else
                {
                    schedule += (0 + "   ");
                }
            }
            schedule += "\n";
        }
        schedule += "1 - free, 0 - reserved";
        return schedule;
        
    }
}