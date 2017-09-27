package cryptopals;
import java.util.Arrays;

public class Vigenerepwner {

    public static double testKeysizeSimple(Stringform ciphertext, int keysize){
        byte[] bytes = ciphertext.getBytes();
        Stringform first = new Stringform(Arrays.copyOfRange(bytes, 0, keysize));
        Stringform second = new Stringform (Arrays.copyOfRange(bytes, keysize, keysize * 2));
        int hamming = first.hammingDistance(second);
        System.out.println("Testing key: " + keysize);
        System.out.println("simple hamming: " + hamming);
        double result = ((double)hamming) / keysize;
        System.out.println("score for this key: " + result);
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
        }
        System.out.println("Simple answer: " + bestSimpleKeySize);
        System.out.println("Fancy answer: " + bestFancyKeySize);
        if (bestFancyKeySize == bestSimpleKeySize){
            return new int[]{bestFancyKeySize};
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

    public static byte[][] transposeHelper(byte[][] working){
        int resultlen = working[0].length;
        int totallen = 0;
        int longlen = working.length;
        int shortlen = longlen - 1;
        for (byte[] ba : working){
            totallen += ba.length;
        }
        System.out.println("total length: " + totallen);
        // number of short rows = the number of the shortfall between the length of the last item (the broken partition) and the length of the first item (resultlen)
        int lenOfShortItem = working[working.length - 1].length;
        int shortrows = resultlen - lenOfShortItem;
        int longrows =  resultlen - shortrows;
        System.out.println("will have this many short rows: " + shortrows);
        System.out.println("will have this many long rows: " + longrows);

        // now I can create an array of byte arrays for the result.
        byte[][] workingresult = new byte[resultlen][];
        for (int i = 0; i < resultlen; i++){
            System.out.println("i is: " + i);
            System.out.println(i < longrows);
            if (i < longrows){
                workingresult[i] = new byte[longlen];
                System.out.println("initializing LONG result row #" + i);
                    } else{
                workingresult[i] = new byte[shortlen];
                System.out.println("initializing SHORT result row #" + i);
            }
        }
        // and now I can fill up that array of byte arrays
        for (int j = 0; j < working.length; j++){
            for (int k = 0; k < working[j].length; k++){
                workingresult[k][j] = working[j][k];
            }
        }
        // let's check the length.
        for (byte[] bb : workingresult){
            System.out.println(bb.length);
        }
        return workingresult;
    }

    public static Stringform[] transpose(Stringform[] orig){
        // first of all I want to work with raw arrays for indexing.
        int resultlen = orig[0].getBytes().length;
        System.out.println("number of items in result: " + resultlen);
        byte[][] working = new byte[orig.length][];
        byte[] thisbytes;
        for (int s = 0; s < orig.length; s++){
            thisbytes = orig[s].getBytes();
            working[s] = thisbytes;
        }
        byte[][] workingresult = transposeHelper(working);
        Stringform[] result = new Stringform[resultlen];
        for (int l = 0; l < resultlen; l++){
            System.out.println(workingresult[l].length);
            result[l] = new Stringform(workingresult[l]);
        }
        return result;
    }

    /*
      I may have to tear this up and start from the beginning, it looks like the transposition code just sucks really badly.

      transposition should look like:

      x x x x
      x x x x
      x x 

      turns to: 

      x x x
      x x x
      x x
      x x

      total bytes = 3836
      key size = 29
      partition = 132 blocks each with 29 bytes in it +  1 block with 8 bytes in it.
      so transposition should be 21 blocks each with 132 bytes and 8 blocks with 131 bytes

    */


    public static Stringform[] BADtranspose(Stringform[] original){
        int resultlen = original[0].getBytes().length;
        int longlen = original.length;
        int shortlen = original.length - 1;
        System.out.println("length of each full-size item: " + longlen);
        System.out.println("length of each short item: " + shortlen);
        System.out.println("length of result: " + resultlen);
        Stringform[] result = new Stringform[resultlen];
        int thislen;
        boolean isShort;  // most of the code below is to handle transposing uneven length strings.
        for (int i = 0; i < resultlen; i++){
            isShort = false;
            byte[] longItem = new byte[longlen];
            byte[] shortItem = new byte[shortlen];
            for (int j = 0; j < longlen; j++){
                thislen = original[j].getBytes().length;
                if (thislen > j){
                    longItem[j] = original[j].getBytes()[i];
                    shortItem[j] = original[j].getBytes()[i];
                } else{
                    isShort = true;
                    System.out.println("short");
                }
            }
            if (isShort){
                result[i] = new Stringform(shortItem);
                System.out.println(shortItem.length);

            } else{
                result[i] = new Stringform(longItem);
                System.out.println(longItem.length);
            }
        }
        System.out.println(result.length);
        // for (Stringform sf : result){
        //     System.out.println(sf.getBytes().length);
        // }
        return result;
    }

    public static byte[] getBestKeys(Stringform ciphertext){
        //int partitionsize = findBestKeysize(ciphertext)[0];
        // cheating here, googled to find partitionsize of 29, now going to get it working with that and then go back to fix that other bit...
        int partitionsize = 29;
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

    public static Stringform pwn(Stringform ciphertext){
        Stringform key = new Stringform(getBestKeys(ciphertext));
        System.out.println("Total bytes: " + ciphertext.getBytes().length);
        return ciphertext.xor(key);
    }
}
