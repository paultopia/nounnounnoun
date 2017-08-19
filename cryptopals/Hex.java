package cryptopals;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Base64;

public class Hex {
    private String hex_str;
    private byte[] byte_array;
    private String decoded_string;
    private String byte_array_string;
    private byte[] base_64_byte_array;
    private String base_64_byte_array_string;
    private String base_64_string;

    public Hex(String myhex){
        hex_str = myhex;
        byte_array = DatatypeConverter.parseHexBinary(hex_str);
        byte_array_string = Arrays.toString(byte_array);
        decoded_string = new String(byte_array);
        base_64_byte_array = Base64.getEncoder().encode(byte_array);
        base_64_byte_array_string = Arrays.toString(base_64_byte_array);
        base_64_string = new String(base_64_byte_array);

    }

    public String getHex(){
        return hex_str;
    }

    public byte[] getByteArray(){
        return byte_array;
    }

    public String getBase64(){
        return base_64_string;
    }

    public void showoff(){
        System.out.println("\nThe original hex string is:\n\n" + hex_str);
        System.out.println("\nHere it is, converted to a byte array:\n\n" + byte_array_string);
        System.out.println("\nHere it is in ascii form:\n\n" + decoded_string);
        System.out.println("\nHere it is converted to bytes again, but after a run through base 64:\n\n" + base_64_byte_array_string);
        System.out.println("\nAnd here's a base 64 string:\n\n" + base_64_string);

    }

    public String roundTrip(){
        return DatatypeConverter.printHexBinary(byte_array);
    }

    public String xor(Hex other){
        int size = byte_array.length;
        byte[] result = new byte[size];
        byte[] other_byte_array = other.getByteArray();
        for (int i = 0; i < size; i++){
            result[i] = (byte)(byte_array[i] ^ other_byte_array[i]);
        } // the cast at the end is necessary because xor apparently promotes to int. see: https://stackoverflow.com/questions/20577609/bitwise-operators-in-java-only-for-integer-and-long 
        return DatatypeConverter.printHexBinary(result);
    }
}
