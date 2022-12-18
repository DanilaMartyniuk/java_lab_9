package server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Handler extends Remote {

    public int getCurrentUserId (String username)throws RemoteException;

    public void appendPending (String resp , int user2, int user)throws RemoteException;

    public String createMeet(int user2, int user)throws RemoteException;

    public void setTime (String day, int time, int user )throws RemoteException;

    public void connectUser(String username)throws RemoteException;

    public void approove(int approoveId, int user) throws RemoteException;
        
    public String showUsers()throws RemoteException;
    
    public int convertDay(String day)throws RemoteException;
    
    public String showPending(int user) throws RemoteException;
        
    public String showSchedule(int user) throws RemoteException;

}