package server;

import components.CommandsObject;
import components.ParserConfigFiles;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
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


    private void start(List<CommandsObject> queueList) {
        try {
            ServerSocket server = new ServerSocket(serverPort);
            while (true) {
                Socket socket = server.accept();
                ServerClientThread sct = new ServerClientThread(socket, queueList);
                sct.start();
            }

        } catch (Exception e) {
            e.getMessage();
        }

    }
}
