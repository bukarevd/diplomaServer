package components;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;

public class CommandObject extends CommandsObject implements Externalizable {
    private static final long serialVersionUID = 1L;
    private static final int VERSION = 1;
    private String name = "";
    private String exec = "";
    private String dependency = "";
    private CommandsObject objectDependecy;


    public CommandObject(String commandString) {
        parsingCommand(commandString);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String getExec() {
        return exec;
    }

    private void setExec(String exec) {
        this.exec = exec;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
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
            System.out.println("Не заполненно поле name у объекта Command");
            System.exit(1);
        }
        if (ValuesHashMap.containsKey("exec"))
            setExec(ValuesHashMap.get("exec"));
        else {
            System.out.println("Не заполненно поле exec у объекта Command");
            System.exit(1);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(VERSION);
        out.writeUTF(getName());
        out.writeUTF(getExec());
        out.writeUTF(getDependency());
        out.writeObject(getObjectDependecy());

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int version = in.readInt();
        if (version > VERSION) {
            throw new IOException("Unsupport version CommandObject");
        }
        setName(in.readUTF());
        setExec(in.readUTF());
        setDependency(in.readUTF());
        setObjectDependecy((CommandsObject) in.readObject());
    }


}
