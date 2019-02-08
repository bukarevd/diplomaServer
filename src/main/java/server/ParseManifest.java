package server;

import components.CommandObject;
import components.CommandsObject;
import components.FileObject;
import components.PackageObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParseManifest {
    private List<CommandsObject> commandObjectList = new ArrayList<>();
    private HashMap<String, CommandsObject> commandsHashMap = new HashMap<>();
    private File manifestFile = new File("/Users/bukarevd/Documents/work.manifest");


    List<CommandsObject> getManifestFile() {
//        Чтение файла манифеста
        System.out.println("Read manifest file");
        try (InputStream in = new FileInputStream(manifestFile);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                byteArrayOutputStream.write(buf, 0, len);
            }
            createCommand(new String(byteArrayOutputStream.toByteArray(), Charset.forName("UTF-8")));
           // createCommand2(new String(byteArrayOutputStream.toByteArray(), Charset.forName("UTF-8")));

        } catch (IOException e) {
            e.getMessage();
        }
        return commandObjectList;
    }

    private void createCommand(String file) {
        //Парсин и добавление объекта команды в List
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
        for (CommandsObject object: commandObjectList) {
           if (object.getDependency().isEmpty())
           {
               System.out.println("empty= " + object.getName());
               continue;
           }
           else {

               object.setObjectDependecy(getObjectDependency(object.getDependency()));
               System.out.println(object.getName() + " = " + object.getObjectDependecy());
           }

//            if (object.getDependency().isEmpty()) continue;
//            else object.setObjectDependecy(getObjectDependency(object.getDependency()));
//            System.out.println(object.getObjectDependecy());
        }
    }

//    private void createCommand2(String file) {
//        //Парсин и добавление объекта команды в List
//        System.out.println("Add objects of manifest in list ");
//        String[] command = file.split("}\n");
//        for (String everyCommand : command) {
//            String[] parseCommand = everyCommand.split("\\{\n");
//            switch (parseCommand[0]) {
//                case "file":
//                    FileObject tempFO = new FileObject(parseCommand[1]);
//                    commandsHashMap.put(tempFO.getName(), tempFO);
//                    break;
//                case "package":
//                    PackageObject tempPO = new PackageObject(parseCommand[1]);
//                    commandsHashMap.put(tempPO.getName(), tempPO);
//                    break;
//                case "command":
//                    CommandObject tempCO = new CommandObject(parseCommand[1]);
//                    commandsHashMap.put(tempCO.getName(), tempCO);
//                    break;
//            }
//        }
//        System.out.println("Set dependencys for objects");
//        for (Map.Entry<String, CommandsObject> entry : commandsHashMap.entrySet()) {
//            if (entry.getValue().getDependency().isEmpty()) continue;
//            else entry.getValue().setObjectDependecy(getObjectDependency(entry.getValue().getDependency()));
//            System.out.println(entry.getValue().getObjectDependecy());
//        }
//        System.out.println(commandsHashMap.toString());
//
//    }

    private CommandsObject getObjectDependency(String objectName) {
        CommandsObject objectDependency = null;

        for (CommandsObject object: commandObjectList) {
            if (object.getName().equals(objectName)) {
                objectDependency = object;
            } else continue;
        }
        return objectDependency;
    }

    private CommandsObject getObjectDependency2(String objectName) {
        CommandsObject objectDependency = null;

       for (Map.Entry<String, CommandsObject> entry : commandsHashMap.entrySet()) {
            if (entry.getValue().getName().equals(objectName)) {
                objectDependency = (CommandsObject) entry.getValue();
            } else continue;
        }
        return objectDependency;
    }


}
