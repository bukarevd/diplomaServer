package components;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;

public class FileObject extends CommandsObject implements Externalizable {
    private static final long serialVersionUID = 1L;
    private static final int VERSION = 1;
    private String name = "";
    private String path = "";
    private String content = "";
    private String owner = "";
    private String group = "";
    private String dependency = "";
    private int chmod;
    private CommandsObject objectDependecy;

    public FileObject(String commandString) {
        parsingCommand(commandString);
    }

    public FileObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String getPath() {
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }

    private String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }

    private String getOwner() {
        return owner;
    }

    private void setOwner(String owner) {
        this.owner = owner;
    }

    private String getGroup() {
        return group;
    }

    private void setGroup(String group) {
        this.group = group;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    private int getChmod() {
        return chmod;
    }

    private void setChmod(int chmod) {
        this.chmod = chmod;
    }

    public CommandsObject getObjectDependecy() {
        return objectDependecy;
    }

    public void setObjectDependecy(CommandsObject objectDependecy) {
        this.objectDependecy = objectDependecy;
    }

    private void parsingCommand(String commandString) {
        HashMap<String, String> commandHashMap = new HashMap<>();
        String[] tempString = commandString.split(";\n");
        for (String str : tempString) {
            String[] keyValue = str.split("=>");
            commandHashMap.put(keyValue[0], keyValue[1]);
        }
        setValue(commandHashMap);

    }


    private void setValue(HashMap<String, String> ValuesHashMap) {
        if (ValuesHashMap.containsKey("name"))
            setName(ValuesHashMap.get("name"));
        else {
            System.out.println("Не заполненно поле name у объекта File");
            System.exit(0);
        }
        if (ValuesHashMap.containsKey("path"))
            setPath(ValuesHashMap.get("path"));
        else {
            System.out.println("Не заполненно поле path у объекта File");
            System.exit(0);
        }
        if (ValuesHashMap.containsKey("content"))
            setContent(ValuesHashMap.get("content"));
        else {
            System.out.println("Не заполненно поле content у объекта File");
            System.exit(0);
        }
        setOwner(ValuesHashMap.get("owner"));
        setGroup(ValuesHashMap.get("group"));
        setChmod(Integer.parseInt(ValuesHashMap.get("chmod")));
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(VERSION);
        out.writeUTF(getName());
        out.writeUTF(getPath());
        out.writeUTF(getContent());
        out.writeUTF(getOwner());
        out.writeUTF(getGroup());
        out.writeUTF(getDependency());
        out.writeInt(getChmod());
        out.writeObject(getObjectDependecy());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int version = in.readInt();
        if (version > VERSION) {
            throw new IOException("Unsupport version FileObject");
        }
        setName(in.readUTF());
        setPath(in.readUTF());
        setContent(in.readUTF());
        setOwner(in.readUTF());
        setGroup(in.readUTF());
        setDependency(in.readUTF());
        setChmod(in.readInt());
        setObjectDependecy((CommandsObject) in.readObject());
    }
}
