package cryptopals;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestVigenerepwner {
    @Test

    public void testTranspose() {
        byte[][] testarray = {{1, 2, 3, 4, 99, 99, 99}, {5, 6, 7, 8, 99, 99, 99}, {9, 10}};
        byte[][] expected = {{1, 5, 9}, {2, 6, 10}, {3, 7}, {4, 8}, {99, 99}, {99, 99}, {99, 99}};
        assertEquals(Vigenerepwner.transpose(testarray), expected);
    }
}
