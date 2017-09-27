package cryptopals;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestScorer {
    @Test

    public void testCP3() {
        Stringform hex = new Stringform("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736", "hex");
        Scorer scorer = new Scorer(" eothasinrdluymwfgcbpkvjqxz");
        String expected = "Cooking MC's like a pound of bacon";
        assertEquals(scorer.calculateBestText(hex), expected);
    }

    public void testCP3Key() {
        Stringform hex = new Stringform("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736", "hex");
        Scorer scorer = new Scorer(" eothasinrdluymwfgcbpkvjqxz");
        byte expected = 88;
        assertEquals(scorer.calculateBestKey(hex), expected);
    }
}
