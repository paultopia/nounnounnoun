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

    // untested.  may be full of off-by-one errors.  step 5 of challenge 6.
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
}
