package cryptopals;
import java.util.HashMap;
import cryptopals.Hex;

public class Scorer{
    private byte[] ranking;
    private HashMap<Integer, Double> points;

    private HashMap<Integer, Double> makePointsMap(){
        HashMap<Integer, Double> result = new HashMap<Integer, Double>();
        for (int i = 0; i < 27; i++){
            result.put((int) ranking[i], Math.sqrt(27 - i));
        }
        return result;
    }

    public Scorer(String rankstring){
        ranking = rankstring.getBytes();
        points = makePointsMap();
    }

    public Double calculateScore(String testee){
        byte[] bytestest = testee.getBytes();
        Double result = 0.0;
        for (int i = 0; i < bytestest.length; i++){
            if (points.containsKey((int) bytestest[i])){
                result += points.get((int) bytestest[i]);
            }
        }
        return result;
    }

    public String calculateBestText(Hex hextext){
        Double max_score = 0.0;
        String best_text = "";
        String current_text = "";
        Double current_score = 0.0;
        for (int i = 0; i < 128; i++){
            current_text = hextext.decodeByChar(i);
            current_score = calculateScore(current_text);
            if (current_score > max_score){
                max_score = current_score;
                best_text = current_text;
            }
        }
        return best_text;
    }
}
