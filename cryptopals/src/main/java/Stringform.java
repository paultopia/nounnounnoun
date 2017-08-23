package cryptopals;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class Stringform {
	private String human = "";
	private String hex = "";
	private String base64 = "";
	private byte[] bytes;
	
	public Stringform(String text, String type){
		switch (type){
			case "hex" : 
				hex = text; 
				bytes = DatatypeConverter.parseHexBinary(text); 
				break;
			case "base64" : 
				base64 = text;
				bytes = DatatypeConverter.parseBase64Binary(text);
				break;
			default :
				human = text;
				bytes = text.getBytes(StandardCharsets.UTF_8);
		}
	}
	
	public Stringform(byte[] inbytes){
		bytes = inbytes;
	}
	
	public Stringform(String text){
		human = text;
		bytes = text.getBytes();
	}
	
	public String getText(){
		if (human == ""){
			human = new String(bytes);
		}
		return human;
	}
	
	public byte[] getBytes(){
		return bytes;
	}
	
	public String getBase64(){
		if (base64 == ""){
        base64 = new String(Base64.getEncoder().encode(bytes));
		}
		return base64;
	}
	
    public String getHex(){
        if (hex == ""){
            hex = DatatypeConverter.printHexBinary(bytes);
        }
        return hex;
    }
    
    public String seeBytes(){
        return Arrays.toString(bytes);
    }
	
	private Stringform unevenXOR(byte[] other_bytes){ // assumes I want to rotate shorter string around to xor longer one, NOT discard unused bits of longer one (which roughly corresponds to the idea that the plaintext will always be longer than the key).  If this assumption becomes false then I'll have to rewrite this, or perhaps just change what gets called from the public xor method below.
		byte[] longer;
		byte[] shorter;
		if (bytes.length > other_bytes.length){
			longer = bytes;
			shorter = other_bytes;
		} else {
			longer = other_bytes;
			shorter = bytes;
		}
		int size = longer.length;
		byte[] result = new byte[size];
		int limit = shorter.length;
		int rot = 0;
		for (int i = 0; i < size; i++){
            result[i] = (byte)(longer[i] ^ shorter[rot]);
            rot ++;
            if (rot == limit){
            	rot = 0;
            }
        }
        return new Stringform(result);
	}
	
	public Stringform xor(Stringform other){
		int size = bytes.length;
        byte[] other_bytes = other.getBytes();
        if (size != other_bytes.length) {
        	return unevenXOR(other_bytes);
        }
		byte[] result = new byte[size];
        for (int i = 0; i < size; i++){
            result[i] = (byte)(bytes[i] ^ other_bytes[i]);
        }
        return new Stringform(result);
	}
	
	public Stringform xor(int charpoint){
		byte charbyte = (byte) charpoint;
        int size = bytes.length;
        byte[] result = new byte[size];
        for (int i = 0; i < size; i++){
            result[i] = (byte)(bytes[i] ^ charbyte);
        }
        return new Stringform(result);
	}
}
