package server;

import components.CommandObject;
import components.CommandsObject;
import components.FileObject;
import components.PackageObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

class ParseManifest {
    private List<CommandsObject> commandObjectList = new ArrayList<>();
    private File manifestFile = new File("/Users/bukarevd/Documents/work.manifest");


    List<CommandsObject> getManifestFile() {
        System.out.println("Read manifest file");
        try (InputStream in = new FileInputStream(manifestFile);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                byteArrayOutputStream.write(buf, 0, len);
            }
            createCommand(new String(byteArrayOutputStream.toByteArray(), Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.getMessage();
        }
        return commandObjectList;
    }

    private void createCommand(String file) {
        System.out.println("Add objects of manifest in list ");
        String[] command = file.split("}\n");
        for (String everyCommand : command) {
            String[] parseCommand = everyCommand.split("\\{\n");
            switch (parseCommand[0]) {
                case "file":
                    commandObjectList.add(new FileObject(parseCommand[1]));
                    break;
                case "package":
                    commandObjectList.add(new PackageObject(parseCommand[1]));
                    break;
                case "command":
                    commandObjectList.add(new CommandObject(parseCommand[1]));
                    break;
            }
        }
        for (CommandsObject object : commandObjectList) {
            if (object.getDependency().isEmpty()) {
                continue;
            } else {

                object.setObjectDependecy(getObjectDependency(object.getDependency()));
            }
        }
    }


    private CommandsObject getObjectDependency(String objectName) {
        CommandsObject objectDependency = null;

        for (CommandsObject object : commandObjectList) {
            if (object.getName().equals(objectName)) {
                objectDependency = object;
            } else continue;
        }
        return objectDependency;
    }
}
