package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

   public static final String UNIQUE_BINDING_NAME = "server.meetings";

   public static void main(String[] args) throws RemoteException, NotBoundException {

       final Registry registry = LocateRegistry.getRegistry(2732);

       Handler handler = (Handler) registry.lookup(UNIQUE_BINDING_NAME);

       
       System.out.println("Enter username");
       Scanner input = new Scanner(System.in);
       String username = input.nextLine();
       handler.connectUser(username);
       
       String command = "";
       int userId = handler.getCurrentUserId(username);
       while(!command.equals("0"))
       {
            System.out.println("1. Adjust schedule \n2. Make an appointment\n3.Approove pending appoinments\n0.exit");
            command = input.nextLine();

            switch(command)
            {
                case("1"):
                {
                    String day, time;
                    System.out.println(handler.showSchedule(userId));
                    while(true)
                    {
                    day = input.nextLine();
                    if(day.equalsIgnoreCase("mon") ||
                     day.equalsIgnoreCase("tue")||
                      day.equalsIgnoreCase("wen") ||
                      day.equalsIgnoreCase("thu") ||
                      day.equalsIgnoreCase("fri") ||
                      day.equalsIgnoreCase("sun") ||
                      day.equalsIgnoreCase("sat"))
                      {
                        break;
                      }
                      else{
                        System.out.println("Try again");
                      }
                    }
                    System.out.println("Choose time");
                    while(true)
                    {
                        time = input.nextLine();
                        if(time.equals("1") ||
                        time.equals("2")||
                        time.equals("3") ||
                        time.equals("4"))
                      {
                        break;
                      }
                      else{
                        System.out.println("Try again");
                      }
                    }

                    handler.setTime(day, Integer.parseInt(time), userId);
                    break;
                }

                case("2"):
                {
                    System.out.println(handler.showUsers());
                    System.out.println("Enter desired user's ID");
                    String id = input.nextLine();
                    String resp = handler.createMeet(Integer.parseInt(id),userId);
                    System.out.println((resp + " approove?\n1. Yes\nElse.No"));
                    if(input.nextLine().equals("1"))
                    {
                        handler.appendPending(resp, Integer.parseInt(id),userId);
                    }
                    break;
                }

                case("3"):
                {
                    System.out.println(handler.showPending(userId));
                    System.out.println("Enter desired meeteng's ID");
                    String id = input.nextLine();
                    if(!id.equals("0")){
                        handler.approove(Integer.parseInt(id),userId);
                    }
                    break;
                }

                case("0"):
                {
                    input.close();
                    System.exit(0);
                }
            }
       }
   }
}