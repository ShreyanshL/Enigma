package enigma;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static enigma.TestUtils.*;
import static org.junit.Assert.assertEquals;

/**
 * The suite of all JUnit tests for the Permutation class.
 *
 * @author Shreyansh Loharuka
 */
public class PermutationTest {

    /**
     * Testing time limit.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /**
     * Check that perm has an alphabet whose size is that of
     * FROMALPHA and TOALPHA and that maps each character of
     * FROMALPHA to the corresponding character of FROMALPHA, and
     * vice-versa. TESTID is used in error messages.
     */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                    e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                    c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                    ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                    ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

    @Test
    public void testPermuteInvert() {
        Alphabet alphabet = new Alphabet();
        String cycle = "(ABDHPEJT)(CFLVMZOYQIRWUKXSG) (N)";
        Permutation permutation = new Permutation(cycle, alphabet);
        assertEquals('P', permutation.permute('H'));

        cycle = "(FIXVYOMW) (CDKLHUP)     (ESZ) (BJ) (GR) (NT) (A) (Q)";
        permutation = new Permutation(cycle, alphabet);
        assertEquals('C', permutation.permute('P'));

        cycle = "(AELTPHQXRU) (BKNW) (CMOY)(DFG) (IV) (JZ) (S)";
        permutation = new Permutation(cycle, alphabet);
        assertEquals('M', permutation.permute('C'));

        cycle = "(ALBEVFCYODJWUGNMQTZSKPR) (HIX)  ";
        permutation = new Permutation(cycle, alphabet);
        assertEquals('Q', permutation.permute('M'));

        cycle = "  (AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO)"
                + " (MP) (RX) (SZ) (TV)";
        permutation = new Permutation(cycle, alphabet);
        assertEquals('D', permutation.permute('Q'));

        cycle = "(ALBEVFCYODJWUGNMQTZSKPR) (HIX)";
        permutation = new Permutation(cycle, alphabet);
        assertEquals('O', permutation.invert('D'));

        cycle = "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)";
        permutation = new Permutation(cycle, alphabet);
        assertEquals('M', permutation.invert('O'));

        cycle = "(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)";
        permutation = new Permutation(cycle, alphabet);
        assertEquals('O', permutation.invert('M'));

        cycle = "(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)";
        permutation = new Permutation(cycle, alphabet);
        assertEquals('Z', permutation.invert('O'));

    }

    @Test
    public void testCleanCycle() {
        Alphabet alphabet = new Alphabet();
        String cycle = "(A B C)";
        Permutation permutation = new Permutation(cycle, alphabet);
        String actual = permutation.cleanCycle(cycle);
        assertEquals("(ABC)", actual);
    }


}
