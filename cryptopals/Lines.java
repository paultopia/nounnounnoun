package cryptopals;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class Lines{
    public static List<String> getLines(String filename){
        List<String> lines = new ArrayList<String>();
        try {
            lines = Files.readAllLines(Paths.get(filename));
        }
        catch (IOException ie){
            System.exit(1);
        }
        return lines;
    }
}
