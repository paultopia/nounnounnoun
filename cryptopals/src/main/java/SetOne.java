package cryptopals;
import java.util.List;

public class SetOne{

    public static void cryptopals1(){
        Stringform hex = new Stringform("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d", "hex"); 
        System.out.println("cryptopals 1: " + hex.getBase64());
    }

    public static void cryptopals2(){
        Stringform hex1 = new Stringform("1c0111001f010100061a024b53535009181c", "hex");
        Stringform hex2 = new Stringform("686974207468652062756c6c277320657965", "hex");
        System.out.println("cryptopals 2: " + hex1.xor(hex2).getHex());
    }

    public static void cryptopals3(){
        Stringform hex = new Stringform("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736", "hex");
        Scorer scorer = new Scorer(" eothasinrdluymwfgcbpkvjqxz");
        System.out.println(scorer.calculateBestText(hex));
    }

    public static void cryptopals4() {
        List<String> lines = Lines.getLines("/4.txt");
        Double max_score = 0.0;
        String best_text = "";
        String current_text = "";
        Double current_score = 0.0;
        Scorer scorer = new Scorer(" eothasinrdluymwfgcbpkvjqxz");
        Stringform current_hex;
        for (String line : lines){
            current_hex = new Stringform(line, "hex");
            current_text = scorer.calculateBestText(current_hex);
            current_score = scorer.calculateScore(current_text); // inefficient, does 1 extra time per hex.
            if (current_score > max_score){
                max_score = current_score;
                best_text = current_text;
            }
        }
        System.out.println(best_text);
    }

    public static void cryptopals5() {
        String vanilla = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal";
        Stringform vtext = new Stringform(vanilla);
        Stringform key = new Stringform("ICE");
        Stringform cyphertext = vtext.xor(key);
        System.out.println(cyphertext.getHex());
    }

    public static void cryptopals6() {
        Stringform first = new Stringform("this is a test");
        Stringform second = new Stringform("wokka wokka!!!");
        System.out.println(first.hammingDistance(second)); // part of the way there... 
    }
}

