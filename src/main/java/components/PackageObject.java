package components;

import java.io.*;
import java.util.HashMap;

public class PackageObject extends CommandsObject implements Externalizable {
    private static final long serialVersionUID = 1L;
    private static final int VERSION = 1;
    String name;
    String version;
    String dependence;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getDependence() {
        return dependence;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDependence(String dependence) {
        this.dependence = dependence;
    }

    public PackageObject(String commandString) {
        parsingCommand(commandString);
    }

    public void parsingCommand(String commandString) {
        HashMap<String, String> commandHashMap = new HashMap<>();
        String[] tempString = commandString.split(";\n");
        for (String str : tempString) {
            String[] keyValue = str.split("=>");
            commandHashMap.put(keyValue[0], keyValue[1]);
        }
        setValue(commandHashMap);
    }


    public void setValue(HashMap<String, String> ValuesHashMap) {
        setName(ValuesHashMap.get("name"));
        setVersion(ValuesHashMap.get("version"));
        setDependence(ValuesHashMap.get("dependence"));
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(VERSION);
        out.writeUTF(getName());
        out.writeUTF(getVersion());
        out.writeUTF(getDependence());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int version = in.readInt();
        if (version > VERSION){
            throw new IOException("Unsupport version PackageObject " + version);
        }
        setName(in.readUTF());
        setVersion(in.readUTF());
        setDependence(in.readUTF());
    }
}
