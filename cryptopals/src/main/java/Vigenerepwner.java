package cryptopals;
import java.util.Arrays;

public class Vigenerepwner {

    public static double testKeysizeSimple(Stringform ciphertext, int keysize){
        byte[] bytes = ciphertext.getBytes();
        Stringform first = new Stringform(Arrays.copyOfRange(bytes, 0, keysize));
        Stringform second = new Stringform (Arrays.copyOfRange(bytes, keysize, keysize * 2));
        int hamming = first.hammingDistance(second);
        return ((double)hamming) / keysize;
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
        double currentSimpleScore;
        double currentFancyScore;
        double bestSimpleScore = Double.MAX_VALUE;
        double bestFancyScore = Double.MAX_VALUE;
        int bestSimpleKeySize = 0;
        int bestFancyKeySize = 0;
        for (int i = 2; i <= 40; i++){
            currentSimpleScore = testKeysizeSimple(ciphertext, i);
            currentFancyScore = testKeysizeFancy(ciphertext, i);
            if (currentSimpleScore < bestSimpleScore){
                bestSimpleScore = currentSimpleScore;
                bestSimpleKeySize = i;
            }
            if (currentFancyScore < bestFancyScore){
                bestFancyScore = currentFancyScore;
                bestFancyKeySize = i;
            }
            if (bestFancyKeySize == bestSimpleKeySize){
                return new int[]{bestFancyKeySize};
            }
        }
            return new int[]{bestFancyKeySize, bestSimpleKeySize};
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

    /* assumes original array of stringforms are all of equal length.

       This assumption may be false (depends on implementation of partition method above---right now I think it zero pads, or maybe null-pads?  whatever Arrays.copyOfRange does.

       It so happens, however, that for the cryptopals challenge that this is written for it doesn't matter, since it's even-numbered and the best keysize by early implementation is 2, so the array will divide evenly.

       But if I were trying to use this for something else I'd really need to fix this.)

       also assumes away all kinds of other edge cases, like zero length etc.
     */
    public static Stringform[] transpose(Stringform[] original){
        int resultlen = original[0].getBytes().length;
        int itemlen = original.length;
        Stringform[] result = new Stringform[resultlen];
        for (int i = 0; i < resultlen; i++){
            byte[] thisItem = new byte[itemlen];
            for (int j = 0; j < itemlen; j++){
                thisItem[j] = original[j].getBytes()[i];
            }
            result[i] = new Stringform(thisItem);
        }
        return result;
    }

    public static int[] getBestKeys(Stringform ciphertext){
        int partitionsize = findBestKeysize(ciphertext)[0]; // only because I know from prior experiments that this yields a length 1 list.
        Stringform[] partitions = partition(ciphertext, 2);
        Stringform[] transposed = transpose(partitions);
        int[] result = new int[transposed.length];
        int currentanswer;
        Scorer scorer = new Scorer(" eothasinrdluymwfgcbpkvjqxz");
        for (int i = 0; i < transposed.length; i++){
            currentanswer = scorer.calculateBestKey(transposed[i]);
            result[i] = currentanswer;
        }
        return result;
    }
}
