package cryptopals;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Base64;

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
				bytes = text.getBytes();
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
		if (base64 = ""){
			base64 = Base64.getEncoder().encode(bytes);
		}
		return base64;
	}
	
	public String getHex(){
		if (hex = ""){
			hex = DatatypeConverter.printHexBinary(bytes);
		}
		return hex;
	}
	
	public Stringform xor(Stringform other){ // this will need to be branched for case of different lengths.  to handle cryptopals 5, I'll just recycle the smaller one.  but first this new class needs to be tested with existing solved challenges. Then I'll just dispatch uneven cases to a separate function.
		int size = bytes.length;
		byte[] result = new byte[size];
        byte[] other_bytes = other.getBytes();
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
        return new String(result);
	}
}