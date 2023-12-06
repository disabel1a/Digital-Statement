package model.users;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UsersBaseFileManager {
    private File file;

    public UsersBaseFileManager(File file) {
        this.file = file;
    }

    public UsersBaseFileManager(String filePath) {
        this.file = new File(filePath);
    }

    public void saveToTextFile(UsersBase usersBase) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(usersBase.toString());
        fw.close();
    }

    public UsersBase loadFromTextFile() throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        UsersBase ub = new UsersBase();
        User user = new User();
        Field[] fields = user.getClass().getDeclaredFields();
        while (br.ready()) {
            String line = br.readLine();
            
            if(line.contains(":")) {
                for (Field f : fields) {
                    String setterName = "set" + capitalizeFirstLetter(f.getName());
                    Method setter = User.class.getMethod(setterName, f.getType());

                    String[] content = line.split(":", 2);

                    System.out.println(content[1].split("\"", 3)[1]);

                    setter.invoke(user, convertToFieldType(content[1].split("\"", 3)[1], f.getType()));
                    line = br.readLine();
                    if (!line.contains(":"))
                        break;
                }
            }
            if (line.contains("[{")) {
                continue;
            }
            ub.addUser(user);
            user = new User();
        }
        br.close();
        return ub;
    }

    private static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static Object convertToFieldType(String value, Class<?> type) {
        if (type == String.class) 
            return value;
        else if (type == Boolean.class)
            return Boolean.parseBoolean(value);
        return null; 
    }

    // public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
    //     UsersBaseFileManager fileManager = new UsersBaseFileManager("src\\test\\java\\model\\Users\\TextFiles\\Load.txt");
    //     UsersBase ub = fileManager.loadFromTextFile();
    //     System.out.println(ub.toString());
    // }
}
