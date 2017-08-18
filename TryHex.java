import cryptopals.Hex;

public class TryHex{
    public static void main(String[] args){
        Hex hex = new Hex("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"); // cryptopals 1
        hex.showoff();
    }
}
