package cryptopals;
import java.util.Arrays;
import java.util.*;

public class Vigenerepwner {

    public static double testKeysizeSimple(Stringform ciphertext, int keysize){
        byte[] bytes = ciphertext.getBytes();
        Stringform first = new Stringform(Arrays.copyOfRange(bytes, 0, keysize));
        Stringform second = new Stringform (Arrays.copyOfRange(bytes, keysize, keysize * 2));
        int hamming = first.hammingDistance(second);
        double result = ((double)hamming) / keysize;
        return result;
    }

    public static double testKeysizeFancy(Stringform ciphertext, int keysize){
        byte[] bytes = ciphertext.getBytes();
        Stringform first = new Stringform(Arrays.copyOfRange(bytes, 0, keysize));
        Stringform second = new Stringform (Arrays.copyOfRange(bytes, keysize, keysize * 2));
        Stringform third = new Stringform(Arrays.copyOfRange(bytes, keysize * 2, keysize * 3));
        Stringform fourth = new Stringform (Arrays.copyOfRange(bytes, keysize * 3, keysize * 4));
        int hamming1 = first.hammingDistance(second);
        int hamming2 = third.hammingDistance(fourth);
        double average = (hamming1 + hamming2) / 2.0;
        return average / keysize;
    }

    public static int[] findBestKeysize(Stringform ciphertext){
        class SizeScorePairing implements Comparable<SizeScorePairing> {
            double score = 0.0;
            int keysize = 0;
            SizeScorePairing(double sc, int ks){
                score = sc;
                keysize = ks;
            }
            public int compareTo(SizeScorePairing other){
                if (this.score - other.score > 0){
                    return 1;
                }
                return -1;
            }
        }
        SizeScorePairing[] scores = new SizeScorePairing[78]; // all the magic numbers aieee
        double simpleScore = 0;
        double fancyScore = 0;
        for (int i = 2; i <= 40; i++){
            simpleScore = testKeysizeSimple(ciphertext, i);
            fancyScore = testKeysizeFancy(ciphertext, i);
            scores[i - 2] = new SizeScorePairing(simpleScore, i);
            scores[77 - (i - 2)] = new SizeScorePairing(fancyScore, i);
        }
        Arrays.sort(scores);
        int[] result = new int[20];
        int targetindex = 0;
        for (int j = 0; j < 20; j++){
            result[j] = scores[j].keysize;
        }
        Set<Integer> set = new HashSet<Integer>();
        for (int num : result) {
            set.add(num);
        }

        Integer[] almostresult = set.toArray(new Integer[set.size()]);

        int[] finalresult = new int[almostresult.length];
        for (int k = 0; k < almostresult.length; k++){
            finalresult[k] = almostresult[k].intValue();
        }
        return finalresult;
    }

    public static Stringform[] partition(Stringform ciphertext, int keysize){
        int numElements;
        byte[] bytes = ciphertext.getBytes();
        int size = bytes.length;
        if (size % keysize == 0){
            numElements = size / keysize;
        } else {
            numElements = (size / keysize) + 1;
        }
        Stringform[] result = new Stringform[numElements];
        byte[] innerarray;
        for (int i = 0; i < numElements; i++){
            if (i != (numElements - 1)){
                innerarray = Arrays.copyOfRange(bytes, keysize * i, keysize * (i + 1));
            } else {
                innerarray =  Arrays.copyOfRange(bytes, keysize * i, size);
            }
            result[i] = new Stringform(innerarray);
        }
        return result;
    }

    public static byte[][] transpose(byte[][] working){
        int resultlen = working[0].length;
        int totallen = 0;
        int longlen = working.length;
        int shortlen = longlen - 1;
        for (byte[] ba : working){
            totallen += ba.length;
        }

        // number of short rows = the number of the shortfall between the length of the last item (the broken partition) and the length of the first item (resultlen)
        int lenOfShortItem = working[working.length - 1].length;
        int shortrows = resultlen - lenOfShortItem;
        int longrows =  resultlen - shortrows;

        // now I can create an array of byte arrays for the result.
        byte[][] workingresult = new byte[resultlen][];
        for (int i = 0; i < resultlen; i++){
            if (i < longrows){
                workingresult[i] = new byte[longlen];
                    } else{
                workingresult[i] = new byte[shortlen];
            }
        }
        // and now I can fill up that array of byte arrays
        for (int j = 0; j < working.length; j++){
            for (int k = 0; k < working[j].length; k++){
                workingresult[k][j] = working[j][k];
            }
        }
        return workingresult;
    }

    public static Stringform[] transpose(Stringform[] orig){
        int resultlen = orig[0].getBytes().length;
        byte[][] working = new byte[orig.length][];
        byte[] thisbytes;
        for (int s = 0; s < orig.length; s++){
            thisbytes = orig[s].getBytes();
            working[s] = thisbytes;
        }
        byte[][] workingresult = transpose(working);
        Stringform[] result = new Stringform[resultlen];
        for (int l = 0; l < resultlen; l++){
            result[l] = new Stringform(workingresult[l]);
        }
        return result;
    }

    public static byte[] getBestKeys(Stringform ciphertext, int partitionsize){
        Stringform[] partitions = partition(ciphertext, partitionsize);
        Stringform[] transposed = transpose(partitions);
        byte[] result = new byte[transposed.length];
        byte currentanswer;
        Scorer scorer = new Scorer(" eothasinrdluymwfgcbpkvjqxz");
        for (int i = 0; i < transposed.length; i++){
            currentanswer = scorer.calculateBestKey(transposed[i]);
            result[i] = currentanswer;
        }
        return result;
    }

    public static Stringform findBestText(Stringform ciphertext){
        Double max_score = 0.0;
        String best_text = "";
        String current_text = "";
        Double current_score = 0.0;
        Stringform currentKey;
        int[] keyoptions = findBestKeysize(ciphertext);
        Scorer scorer = new Scorer(" eothasinrdluymwfgcbpkvjqxz");
        for (int option: keyoptions){
            currentKey = new Stringform(getBestKeys(ciphertext, option));
            current_text = ciphertext.xor(currentKey).getText();
            current_score = scorer.calculateScore(current_text);
            if (current_score > max_score){
                max_score = current_score;
                best_text = current_text;
            }
        }
        return new Stringform(best_text);
    }

    public static Stringform pwn(Stringform ciphertext){
        //Stringform key = new Stringform(getBestKeys(ciphertext, 29));
        //Stringform key = new Stringform("Terminator X: Bring the noise");
        //System.out.println("Total bytes: " + ciphertext.getBytes().length);
        //return ciphertext.xor(key);
        return findBestText(ciphertext);
    }
}
