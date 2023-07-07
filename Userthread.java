
package sample;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.net.*;
import java.io.*;

public class Userthread extends Thread {

    private ArrayList<Userthread> users;

    private Socket socket;
    //private ChatServer server;

    /**
     private ObjectOutputStream ous;
     private ObjectInputStream ins;**/

    private BufferedReader reader;
    private PrintWriter writer;

    public Userthread(Socket socket, ArrayList<Userthread> users)
    {
        try
        {
            this.socket = socket;
            this.users = users;

            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            //this.ous = new ObjectOutputStream(socket.getOutputStream());
            //this.ins = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            /**
             FileWriter fw = new FileWriter("chatlog.txt");

             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
             ous = new ObjectOutputStream(outputStream);

             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             writer = new PrintWriter(outputStream, true);

             //printAllUsers();
             //FilePacket packet = (FilePacket) objectInputStream.readObject();

             /**
             String username = packet.getMessage();
             server.addUsername(username);
             server.broadcast(new FilePacket("NEW USER CONNECTED! WELCOME "+ username) , this);
             String message;
             do {
             FilePacket received = (FilePacket) objectInputStream.readObject();
             message = received.getMessage();
             //servermessage = username + ": " + message + "\n";
             server.broadcast(received, this);

             } while (!message.equals("bye"));
             server.removeUser(username, this);
             socket.close();
             FilePacket exitMessage = new FilePacket("The user " + username + " has left the server");
             server.broadcast(exitMessage, this);
             **/

            //SAVE=======================================================

            FileWriter fw = new FileWriter("chatlog.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            String message;
            do {

                message = reader.readLine();

                System.out.println("console : " + message);

                LocalDateTime timeStamp = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                String timestampstring = timeStamp.format(formatter);

                System.out.println(timestampstring);

                out.println(timestampstring);
                out.println(message);

                /**
                 if (message.equalsIgnoreCase("quit"))
                 {
                 break;
                 }**/

                for (Userthread u : users)
                {
                    u.writer.println(message);
                }

            } while(message != null);

            // SAVE=======================================================
            /**
             String message;

             while((message = reader.readLine()) != null)
             {
             LocalDateTime timeStamp = LocalDateTime.now();
             DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
             String timestampstring = timeStamp.format(formatter);
             System.out.println(timestampstring + "\n");

             if (message.equalsIgnoreCase("quit"))
             {
             break;
             }

             for (Userthread u : users)
             {
             u.writer.println(message);
             }
             System.out.println("message : "+ message);

             } **/

        }
        catch (IOException exception)
        {
            System.out.println("Error in UserThread IOexception: " + exception.getMessage());
            exception.printStackTrace();
        }
        /**
         catch (ClassNotFoundException exception)
         {
         System.out.println("Error in UserThread ClassNotFound Exception: "+exception.getMessage());
         exception.printStackTrace();
         }
         **/
        finally
        {
            try {
                reader.close();
                writer.close();
                socket.close();
                //System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     void sendMessage(FilePacket message){
     try{
     ous.writeObject(message);
     }
     catch (IOException exception){
     System.out.println("Error in UserThread sendMessage: " + exception.getMessage());
     exception.printStackTrace();
     }

     }

     void printAllUsers()
     {
     try {
     if (server.noUsers()) {
     ous.writeObject(new FilePacket("\nUSERS CONNCETED: " + server.getNames()));
     } else {
     ous.writeObject(new FilePacket("\nNO USERS CONNECTED"));
     }
     }
     catch (IOException exception){
     System.out.println("Error in UserThread sendMessage: " + exception.getMessage());
     exception.printStackTrace();
     }
     }
     **/

}


