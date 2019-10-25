package enigma;

import org.junit.Test;

public class ReadRotorTest {
    @Test
    public void rotorTest() {
        String[] asdf = new String[]{"./testing/correct/rotor.conf"};
        Main test = new Main(asdf);
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());
    }

    @Test
    public void readConfigTest() {
        String[] test = new String[]{"./testing/correct/default.conf"};
        Main default1 = new Main(test);
        System.out.println(default1.readConfig());
    }

    @Test
    public void rotorTest2() {
        String[] asdf = new String[]{"./testing/correct/01-Newchars.conf"};
        Main test = new Main(asdf);
        System.out.println(test.readRotor());
        System.out.println(test.readRotor());

    }

    @Test
    public void readConfigTest2() {
        String[] test = new String[]{"./testing/error/FaultyCycles.conf"};
        Main default1 = new Main(test);
        System.out.println(default1.readConfig());
    }

}
