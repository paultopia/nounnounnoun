import cryptopals.Hex;
import cryptopals.Scorer;

public class TryHex{

    public static void cryptopals1(){
        Hex hex = new Hex("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"); // cryptopals 1
        System.out.println("cryptopals 1: " + hex.getBase64());
    }

    public static void cryptopals2(){
        Hex hex1 = new Hex("1c0111001f010100061a024b53535009181c");
        Hex hex2 = new Hex("686974207468652062756c6c277320657965");
        System.out.println("cryptopals 2: " + hex1.xor(hex2));
    }

    public static void cryptopals3(){
        Hex hex = new Hex("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736");
        Double max_score = 0.0;
        String best_text = "";
        String current_text = "";
        Double current_score = 0.0;
        Scorer scorer = new Scorer();
        for (int i = 0; i < 128; i++){
            current_text = hex.decodeByChar(i);
            current_score = scorer.calculateScore(current_text);  
            if (current_score > max_score){
                max_score = current_score;
                best_text = current_text;
            }
        }
        System.out.println(best_text);
    }

    public static void main(String[] args){
        //cryptopals1();
        //cryptopals2();
        cryptopals3();
    }
}
