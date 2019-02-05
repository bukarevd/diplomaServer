package server;

import components.CommandsObject;
import components.DiplomaApp;
import components.ParserConfigFiles;

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

    public static Server getInstance() {
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


    void start(List<CommandsObject> quiuiList) {
        try (ServerSocket server = new ServerSocket(serverPort)) {
            System.out.println(quiuiList);
            Socket socket = server.accept();
            System.out.println(quiuiList);
            sendMessage(socket, quiuiList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //    Отправка объектов на клиент для выполнения
    void sendMessage(Socket socket, List<CommandsObject> quiuiList) {
        try {
            while (!socket.isClosed()) {
                ObjectOutputStream outServer = new ObjectOutputStream(socket.getOutputStream());
              // for (CommandsObject command : quiuiList) {
                    System.out.println(quiuiList.get(2));
                    outServer.writeObject(quiuiList.get(2));
                    outServer.flush();
              //  }
                outServer.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}