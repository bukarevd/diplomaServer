package components;

import server.Server;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;

public class ParserConfigFiles {
    private DiplomaApp app;
    private File fileConfig;

    public ParserConfigFiles(DiplomaApp app) {
        this.app = app;
    }

    public void getConfig() {
        if (app instanceof Server) {
            readServerFile((Server) app);
        }
    }

    private void readServerFile(Server server) {
        fileConfig = server.getSERVERCONFIG();
        String configServerString = reader(fileConfig);
        setValue(server, configServerString);

    }

//    private void readClientFile(Client client) {
//        fileConfig = new File(client.getCLIENTCONFIG());
//        String configClientString = reader(fileConfig);
//        setValue(client, configClientString);
//
//    }

    private String reader(File file) {
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
        HashMap<String, String> configValues = new HashMap<>();
        String[] configArray = string.split("\n");
        for (String str : configArray) {
            String[] tempString = str.split("=>");
            configValues.put(tempString[0], tempString[1]);
        }
        server.setServerPort(Integer.parseInt(configValues.get("port")));
    }

}
