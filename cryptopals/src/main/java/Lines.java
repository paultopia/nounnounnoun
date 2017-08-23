package cryptopals;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;


public class Lines{
    public static List<String> getLines(String filename){
        List<String> lines = new ArrayList<String>();
        String line;
        try {
            InputStream in = Lines.class.getResourceAsStream(filename); // note to self: this requires directory with slash, or plain slash if in root directory, appended to filename.  Ref: https://stackoverflow.com/questions/14739550/difference-between-getclass-getclassloader-getresource-and-getclass-getres
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
        }
        catch (IOException ie){
            System.exit(1);
        }
        return lines;
    }
}
