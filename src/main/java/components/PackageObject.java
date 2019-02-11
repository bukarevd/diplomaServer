package components;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;

public class PackageObject extends CommandsObject implements Externalizable {
    private static final long serialVersionUID = 1L;
    private static final int VERSION = 1;
    private String name = "";
    private String action = "";
    private String version = "";
    private String dependency = "";
    private CommandsObject objectDependecy;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private String getVersion() {
        return version;
    }

    public String getDependency() {
        return dependency;
    }

    private void setVersion(String version) {
        this.version = version;
    }

    public void setDependency(String dependence) {
        this.dependency = dependence;
    }

    public CommandsObject getObjectDependecy() {
        return objectDependecy;
    }

    public void setObjectDependecy(CommandsObject objectDependecy) {
        this.objectDependecy = objectDependecy;
    }

    public PackageObject(String commandString) {
        parsingCommand(commandString);
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
            System.out.println("Не заполненно поле name у объекта Package");
            System.exit(0);
        }
        if (ValuesHashMap.containsKey("action"))
            setAction(ValuesHashMap.get("action"));
        else {
            System.out.println("Не заполненно поле action у объекта Package");
            System.exit(0);
        }
        if (ValuesHashMap.containsKey("version"))
            setVersion(ValuesHashMap.get("version"));
        if (ValuesHashMap.containsKey("dependence"))
            setDependency(ValuesHashMap.get("dependence"));

    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(VERSION);
        out.writeUTF(getName());
        out.writeUTF(getAction());
        out.writeUTF(getVersion());
        out.writeUTF(getDependency());
        out.writeObject(getObjectDependecy());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int version = in.readInt();
        if (version > VERSION) {
            throw new IOException("Unsupport version PackageObject " + version);
        }
        setName(in.readUTF());
        setAction(in.readUTF());
        setVersion(in.readUTF());
        setDependency(in.readUTF());
        setObjectDependecy((CommandsObject) in.readObject());
    }
}
