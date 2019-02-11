package components;

import server.Server;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;

public class ParserConfigFiles {
    private Server server;

    public Server getServer() {
        return server;
    }

    public ParserConfigFiles(Server server) {
        this.server = server;
    }

    public void getConfig() {
        readServerFile(getServer());
    }

    private void readServerFile(Server server) {
        File fileConfig = server.getSERVERCONFIG();
        String configServerString = reader(fileConfig);
        setValue(server, configServerString);
    }

    private String reader(File file) {
        System.out.println("Read server config file");
        String str = null;
        try (InputStream in = new FileInputStream(file);
             ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                bout.write(buf, 0, len);
                str = new String(bout.toByteArray(), Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return str;
    }

    private void setValue(Server server, String string) {
        System.out.println("Parsing server config file");
        HashMap<String, String> configValues = new HashMap<>();
        String[] configArray = string.split("\n");
        for (String str : configArray) {
            String[] tempString = str.split("=>");
            configValues.put(tempString[0], tempString[1]);
        }
        server.setServerPort(Integer.parseInt(configValues.get("port")));
    }

}
