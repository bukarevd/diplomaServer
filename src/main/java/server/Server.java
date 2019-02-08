package server;

import components.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server extends DiplomaApp {
    private File SERVERCONFIG = new File("/Users/bukarevd/Documents/server.conf");
    private int serverPort;
    private static Server instances;


    private Server() {
    }

    private static Server getInstance() {
        if (instances == null) instances = new Server();
        return instances;
    }

    public File getSERVERCONFIG() {
        return SERVERCONFIG;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public static void main(String[] args) {
        Server server = getInstance();
        ParserConfigFiles serverConfig = new ParserConfigFiles(server);
        serverConfig.getConfig();
        ParseManifest parseManifest = new ParseManifest();
        server.start(parseManifest.getManifestFile());
    }


    private void start(List<CommandsObject> quiuiList) {
        try (ServerSocket server = new ServerSocket(serverPort)) {
            Socket socket = server.accept();
            sendMessage(socket, quiuiList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //    Отправка объектов на клиент для выполнения
    private void sendMessage(Socket socket, List<CommandsObject> quiuiList) {
        System.out.println("Send objects:");
        try {
            while (!socket.isClosed()) {
                ObjectOutputStream outServer = new ObjectOutputStream(socket.getOutputStream());
                for (CommandsObject commandsObject: quiuiList) {
                    System.out.println(commandsObject.getName());
                    outServer.writeObject(commandsObject);
                    outServer.flush();
                }
                outServer.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
