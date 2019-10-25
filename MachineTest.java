package enigma;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MachineTest {
    /**
     * Testing time limit.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    @Test
    public void testIn1() {
        Alphabet alphabet = new Alphabet();
        Permutation perm =
                new Permutation("(AE) (BN) (CK) (DQ) (FU)"
                        + " (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)",
                        alphabet);
        Reflector b = new Reflector("B", perm);

        Permutation perm2 =
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)",
                        alphabet);
        FixedRotor beta =
                new FixedRotor("Beta", perm2);

        Permutation perm3 =
                new Permutation("(AELTPHQXRU) (BKNW)"
                        + " (CMOY) (DFG) (IV) (JZ) (S)",
                        alphabet);
        MovingRotor aI =
                new MovingRotor("I", perm3, "Q");

        Permutation perm4 =
                new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ)"
                        + " (BJ) (GR) (NT) (A) (Q)", alphabet);
        MovingRotor aII =
                new MovingRotor("II", perm4, "E");

        Permutation perm5 =
                new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", alphabet);
        MovingRotor aIII =
                new MovingRotor("III", perm5, "V");

        ArrayList<Rotor> rotor = new ArrayList<Rotor>();
        rotor.add(b);
        rotor.add(beta);
        rotor.add(aI);
        rotor.add(aII);
        rotor.add(aIII);

        Machine enigma = new Machine(alphabet, 5, 3, rotor);
        String[] rotors = new String[]{"B", "Beta", "I", "II", "III"};
        enigma.insertRotors(rotors);
        assertArrayEquals(new Rotor[]{b, beta, aI, aII, aIII},
                enigma.rotorSlots());
        enigma.setRotors("AAAA");
        String actual = enigma.convert("HELLO WORLD");
        String expect = "ILBDA AMTAZ";
        assertEquals(expect, actual);

        enigma.setRotors("AAAA");
        String actual2 = enigma.convert("ILBDA AMTAZ");
        String expect2 = "HELLO WORLD";
        assertEquals(expect2, actual2);
    }

    @Test
    public void testOut1() {
        Alphabet alphabet = new Alphabet();
        Permutation perm =
                new Permutation("(AE) (BN) (CK) (DQ) (FU)"
                        +
                        " (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)",
                        alphabet);
        Reflector b = new Reflector("B", perm);

        Permutation perm2 =
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR)"
                        +
                        " (HIX)",
                        alphabet);
        FixedRotor beta =
                new FixedRotor("Beta", perm2);

        Permutation perm3 =
                new Permutation("(AELTPHQXRU) (BKNW)"
                        +
                        " (CMOY) (DFG) (IV) (JZ) (S)",
                        alphabet);
        MovingRotor aI = new MovingRotor("I", perm3, "Q");

        Permutation perm4 =
                new Permutation("(FIXVYOMW) (CDKLHUP)"
                        +
                        " (ESZ) (BJ) (GR) (NT) (A) (Q)",
                        alphabet);
        MovingRotor aII = new MovingRotor("II", perm4, "E");

        Permutation perm5 =
                new Permutation("(ABDHPEJT)"
                        +
                        " (CFLVMZOYQIRWUKXSG) (N)",
                        alphabet);
        MovingRotor aIII = new MovingRotor("III", perm5, "V");

        ArrayList<Rotor> rotor = new ArrayList<Rotor>();
        rotor.add(b);
        rotor.add(beta);
        rotor.add(aI);
        rotor.add(aII);
        rotor.add(aIII);

        Machine enigma =
                new Machine(alphabet, 5, 3, rotor);
        String[] rotors = new String[]{"B", "Beta", "I", "II", "III"};
        enigma.insertRotors(rotors);
        assertArrayEquals(new Rotor[]{b, beta, aI, aII, aIII},
                enigma.rotorSlots());
        enigma.setRotors("AAAA");
        String actual = enigma.convert("ILBDA AMTAZ");
        String expect = "HELLO WORLD";
        assertEquals(expect, actual);
    }

    @Test
    public void testIn2() {
        Alphabet alphabet = new Alphabet();
        Permutation perm =
                new Permutation("(AE) (BN) (CK) (DQ) (FU)"
                        +
                        " (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)",
                        alphabet);
        Reflector b = new Reflector("B", perm);

        Permutation perm2 =
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)",
                        alphabet);
        FixedRotor beta = new FixedRotor("Beta", perm2);

        Permutation perm3 =
                new Permutation("(AELTPHQXRU)"
                        +
                        " (BKNW) (CMOY) (DFG) (IV) (JZ) (S)",
                        alphabet);
        MovingRotor aI = new MovingRotor("I", perm3, "Q");

        Permutation perm4 =
                new Permutation("(FIXVYOMW)"
                        +
                        " (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)",
                        alphabet);
        MovingRotor aII = new MovingRotor("II", perm4, "E");

        Permutation perm5 =
                new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)",
                        alphabet);
        MovingRotor aIII = new MovingRotor("III", perm5, "V");

        Permutation plugboard = new Permutation("(AQ) (EP)", alphabet);

        ArrayList<Rotor> rotor = new ArrayList<Rotor>();
        rotor.add(b);
        rotor.add(beta);
        rotor.add(aI);
        rotor.add(aII);
        rotor.add(aIII);

        Machine enigma = new Machine(alphabet, 5, 3, rotor);
        String[] rotors = new String[]{"B", "Beta", "I", "II", "III"};
        enigma.insertRotors(rotors);
        assertArrayEquals(new Rotor[]{b, beta, aI, aII, aIII},
                enigma.rotorSlots());
        enigma.setRotors("AAAA");
        enigma.setPlugboard(plugboard);
        String actual = enigma.convert("HELLO WORLD");
        String expect = "IHBDQ QMTQZ";
        assertEquals(expect, actual);
    }

    @Test
    public void testOut2() {
        Alphabet alphabet = new Alphabet();
        Permutation perm = new Permutation("(AE) (BN) (CK)"
                + " (DQ) (FU) (GY) (HW) (IJ)"
                +
                " (LO) (MP) (RX) (SZ) (TV)", alphabet);
        Reflector B = new Reflector("B", perm);

        Permutation perm2 =
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", alphabet);
        FixedRotor beta = new FixedRotor("Beta", perm2);

        Permutation perm3 = new Permutation("(AELTPHQXRU)"
                +
                " (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", alphabet);
        MovingRotor aI = new MovingRotor("I", perm3, "Q");

        Permutation perm4 = new Permutation("(FIXVYOMW)"
                +
                " (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", alphabet);
        MovingRotor aII = new MovingRotor("II", perm4, "E");

        Permutation perm5 = new Permutation("(ABDHPEJT)"
                +
                " (CFLVMZOYQIRWUKXSG) (N)", alphabet);
        MovingRotor aIII = new MovingRotor("III", perm5, "V");

        Permutation plugboard = new Permutation("(AQ) (EP)", alphabet);

        ArrayList<Rotor> rotor = new ArrayList<Rotor>();
        rotor.add(B);
        rotor.add(beta);
        rotor.add(aI);
        rotor.add(aII);
        rotor.add(aIII);

        Machine enigma =
                new Machine(alphabet, 5, 3, rotor);
        String[] rotors = new String[]{"B", "Beta", "I", "II", "III"};
        enigma.insertRotors(rotors);
        assertArrayEquals(new Rotor[]{B, beta, aI, aII, aIII},
                enigma.rotorSlots());
        enigma.setRotors("AAAA");
        enigma.setPlugboard(plugboard);
        String actual = enigma.convert("IHBDQ QMTQZ");
        String expect = "HELLO WORLD";
        assertEquals(expect, actual);
    }

    @Test
    public void testIn3() {
        Alphabet alphabet = new Alphabet();
        Permutation perm = new Permutation("(AE) (BN) (CK)"
                +
                " (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", alphabet);
        Reflector B = new Reflector("B", perm);

        Permutation perm2 = new Permutation("(ALBEVFCYODJWUGNMQTZSKPR)"
                +
                " (HIX)", alphabet);
        FixedRotor beta = new FixedRotor("Beta", perm2);

        Permutation perm3 = new Permutation("(AELTPHQXRU)"
                +
                " (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", alphabet);
        MovingRotor aI = new MovingRotor("I", perm3, "B");

        Permutation perm4 = new Permutation("(FIXVYOMW)"
                +
                " (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", alphabet);
        MovingRotor aII = new MovingRotor("II", perm4, "B");

        Permutation perm5 = new Permutation("(ABDHPEJT)"
                +
                " (CFLVMZOYQIRWUKXSG) (N)", alphabet);
        MovingRotor aIII = new MovingRotor("III", perm5, "C");

        ArrayList<Rotor> rotor = new ArrayList<Rotor>();
        rotor.add(B);
        rotor.add(beta);
        rotor.add(aI);
        rotor.add(aII);
        rotor.add(aIII);

        Machine enigma =
                new Machine(alphabet, 5, 3, rotor);
        String[] rotors = new String[]{"B", "Beta", "I", "II", "III"};
        enigma.insertRotors(rotors);
        assertArrayEquals(new Rotor[]{B, beta, aI, aII, aIII},
                enigma.rotorSlots());
        enigma.setRotors("AAAA");
        String actual = enigma.convert("HE");
        String expect = "IL";
        assertEquals(expect, actual);
    }

    @Test
    public void testOut3() {
        Alphabet alphabet = new Alphabet();
        Permutation perm = new Permutation("(AE) (BN) (CK)"
                +
                " (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", alphabet);
        Reflector B = new Reflector("B", perm);

        Permutation perm2 =
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", alphabet);
        FixedRotor beta = new FixedRotor("Beta", perm2);

        Permutation perm3 = new Permutation("(AELTPHQXRU)"
                +
                " (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", alphabet);
        MovingRotor aI = new MovingRotor("I", perm3, "B");

        Permutation perm4 = new Permutation("(FIXVYOMW)"
                +
                " (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", alphabet);
        MovingRotor aII = new MovingRotor("II", perm4, "B");

        Permutation perm5 =
                new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", alphabet);
        MovingRotor aIII = new MovingRotor("III", perm5, "C");

        ArrayList<Rotor> rotor = new ArrayList<Rotor>();
        rotor.add(B);
        rotor.add(beta);
        rotor.add(aI);
        rotor.add(aII);
        rotor.add(aIII);

        Machine enigma = new Machine(alphabet, 5, 3, rotor);
        String[] rotors = new String[]{"B", "Beta", "I", "II", "III"};
        enigma.insertRotors(rotors);
        assertArrayEquals(new Rotor[]{B, beta, aI, aII, aIII},
                enigma.rotorSlots());
        enigma.setRotors("AAAA");
        String actual = enigma.convert("IL");
        String expect = "HE";
        assertEquals(expect, actual);
    }



    @Test
    public void testNAVAL() {
        Alphabet alphabet = new Alphabet();
        Permutation perm = new Permutation("(AE) (BN) (CK)"
                +
                " (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", alphabet);
        Reflector B = new Reflector("B", perm);

        Permutation perm2 =
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", alphabet);
        FixedRotor beta = new FixedRotor("Beta", perm2);

        Permutation perm3 = new Permutation("(AELTPHQXRU)"
                +
                " (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", alphabet);
        MovingRotor aI = new MovingRotor("I", perm3, "Q");

        Permutation perm4 = new Permutation("(AEPLIYWCOXMRFZBSTGJQNH)"
                +
                " (DV) (KU)", alphabet);
        MovingRotor aIV = new MovingRotor("IV", perm4, "J");

        Permutation perm5 = new Permutation("(ABDHPEJT)"
                +
                " (CFLVMZOYQIRWUKXSG) (N)", alphabet);
        MovingRotor aIII = new MovingRotor("III", perm5, "V");

        Permutation plugboard =
                new Permutation("(HQ) (EX) (IP) (TR) (BY)", alphabet);

        ArrayList<Rotor> rotor = new ArrayList<Rotor>();
        rotor.add(B);
        rotor.add(beta);
        rotor.add(aI);
        rotor.add(aIV);
        rotor.add(aIII);

        Machine enigma = new Machine(alphabet, 5, 3, rotor);
        String[] rotors = new String[]{"B", "Beta", "III", "IV", "I"};
        enigma.insertRotors(rotors);
        assertArrayEquals(new Rotor[]{B, beta, aIII, aIV, aI},
                enigma.rotorSlots());
        enigma.setRotors("AXLE");
        enigma.setPlugboard(plugboard);
        String actual = enigma.convert("FROM");
        System.out.println(actual);
    }


}
