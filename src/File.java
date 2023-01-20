import java.io.*;

public class File {
double ProgramTime;
    public File(double ProgramTime) {
        this.ProgramTime=ProgramTime;
    }

    public static void main(String[] args) {


        try(FileWriter writer = new FileWriter("src/Time.txt", true))
        {
            // запись всей строки
            String text = "Hello Gold!";
            writer.write(text);
            // запись по символам
            writer.append('\n');
//            writer.append('E');

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}