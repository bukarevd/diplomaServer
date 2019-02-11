package server;

import components.CommandsObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerClientThread extends Thread {
    Socket serverClient;
    List<CommandsObject> queueList;


    ServerClientThread(Socket inSocket, List<CommandsObject> inList) {
        serverClient = inSocket;
        queueList = inList;
    }

    public void run() {
        System.out.println("Send objects: " + Thread.currentThread().getName());
        try {
            while (!serverClient.isClosed()) {
                ObjectOutputStream outServer = new ObjectOutputStream(serverClient.getOutputStream());
                for (CommandsObject commandsObject : queueList) {
                    System.out.println(commandsObject.getName());
                    outServer.writeObject(commandsObject);
                    outServer.flush();
                }
                outServer.close();
                serverClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
