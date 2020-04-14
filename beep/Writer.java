import java.io.*;
import java.time.*;

public class Writer {
    FileWriter fw;
    File file;
    public Writer() {
        file = new File("C:\\Users\\Ben\\Desktop\\Math IA\\data\\" + LocalDateTime.now().getHour() + "-" + LocalDateTime.now().getMinute() + "-" + LocalDateTime.now().getSecond() + ".txt");
        try {
            if (file.createNewFile())
                System.out.println("File created succesfully!");
            else
                System.out.println("File already exists...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void toTxt(String s) {
        try {
            fw = new FileWriter(file);
            fw.write(s);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}