package cryptopals;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

public class Hex {
    private String hex_str;
    private byte[] byte_array;
    private String decoded_string;
    private String byte_array_string;

    public Hex(String myhex){
        hex_str = myhex;
        byte_array = DatatypeConverter.parseHexBinary(hex_str);
        byte_array_string = Arrays.toString(byte_array);
        decoded_string = new String(byte_array);

    }

    public String getHex(){
        return hex_str;
    }

    public void printbytes(){
        System.out.println(byte_array_string);
        System.out.println(decoded_string);

    }
}
