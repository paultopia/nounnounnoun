package cryptopals;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

public class Hex {
    private String hex_str;

    public Hex(String myhex){
        hex_str = myhex;
    }

    public String getHex(){
        return hex_str;
    }

    public void printbytes(){
        byte[] b = DatatypeConverter.parseHexBinary(hex_str); 
        System.out.println(Arrays.toString(b));

    }
}
