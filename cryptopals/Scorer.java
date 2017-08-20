package cryptopals;
import java.util.HashMap;

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
}
