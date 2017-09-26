package cryptopals;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestStringform {
    @Test

    public void testHammingDistance() {
        Stringform first = new Stringform("this is a test");
        Stringform second = new Stringform("wokka wokka!!!");
        int expected = 37;
        assertEquals(first.hammingDistance(second), expected);
    }
}
