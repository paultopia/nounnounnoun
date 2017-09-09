package cryptopals;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;


public class Lines{
    public static List<String> getLines(String filename){
        List<String> lines = new ArrayList<String>();
        String line;
        try (InputStream in = Lines.class.getResourceAsStream(filename); 
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
        }
        catch (IOException ie){
            System.exit(1);
        }
        return lines;
    }

    public static String getString(String filename){
        List<String> lines = getLines(filename);
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line);
        }
        return sb.toString();
    }
}

// note to self: class.getResourceAsStream() requires directory with slash, or plain slash if in root directory, appended to filename. Ref: https://stackoverflow.com/questions/14739550/difference-between-getclass-getclassloader-getresource-and-getclass-getres And it would be getClas().getResourceAsStream() outside a static method I think?
