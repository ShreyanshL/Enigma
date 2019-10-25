package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.error;

/**
 * Enigma simulator.
 *
 * @author Shreyansh Loharuka
 */
public final class Main {

    /**
     * Process a sequence of encryptions and decryptions, as
     * specified by ARGS, where 1 <= ARGS.length <= 3.
     * ARGS[0] is the name of a configuration file.
     * ARGS[1] is optional; when present, it names an input file
     * containing messages.  Otherwise, input comes from the standard
     * input.  ARGS[2] is optional; when present, it names an output
     * file for processed messages.  Otherwise, output goes to the
     * standard output. Exits normally if there are no errors in the input;
     * otherwise with code 1.
     */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /**
     * Check ARGS and open the necessary files (see comment on main).
     */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /**
     * Return a Scanner reading from the file named NAME.
     */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Return a PrintStream writing to the file named NAME.
     */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Configure an Enigma machine from the contents of configuration
     * file _config and apply it to the messages in _input, sending the
     * results to _output.
     */
    private void process() {
        Machine machine = this.readConfig();
        String asterisk = this._input.next();
        if (asterisk.charAt(0) != '*') {
            throw new EnigmaException("No Configuration");
        }
        while (this._input.hasNextLine()) {
            String get = this._input.nextLine();
            if (get.equals("")) {
                _output.println();
                continue;
            }
            if (asterisk.equals("*") || get.charAt(0) == '*') {
                asterisk = "";
                get = get.substring(1).trim();
                String[] setup = get.split("\\s");

                if (setup.length < machine.numRotors() + 1) {
                    throw new EnigmaException("Wrong arguments in setup line");
                }

                String[] rotors = new String[machine.numRotors()];
                System.arraycopy(setup, 0, rotors, 0, machine.numRotors());

                machine.insertRotors(rotors);
                this.setUp(machine, setup[machine.numRotors()]);
                int p = 1;
                if (setup.length > machine.numRotors() + 1) {
                    if (!setup[machine.numRotors() + 1].contains("(")) {
                        p++;
                        machine.setOffset(setup[machine.numRotors() + 1]);
                    }
                }
                String plug = "";
                for (int i = machine.numRotors() + p; i < setup.length; i++) {
                    plug = plug + " " + setup[i];
                }
                if (!plug.equals("")) {
                    Permutation plugboard = new Permutation(plug, _alphabet);
                    machine.setPlugboard(plugboard);
                }
            } else {
                String converted = machine.convert(get).trim();
                printMessageLine(converted);
            }
        }
        this._config.close();
    }

    /**
     * Return an Enigma machine configured from the contents of configuration
     * file _config.
     */
    public Machine readConfig() {
        try {
            ArrayList<Rotor> allRotors = new ArrayList<Rotor>();
            int pawls = 0, numrotors = 0;
            this._alphabet = new Alphabet(this._config.next());
            if (!this._config.hasNextInt()) {
                throw new EnigmaException("Enter numRotors after alphabets!");
            }
            numrotors = this._config.nextInt();
            if (!this._config.hasNextInt()) {
                throw new EnigmaException("Enter the numPawls after rotors!");
            }
            pawls = this._config.nextInt();
            while (this._config.hasNext()) {
                allRotors.add(this.readRotor());
            }
            this._config.close();
            return new Machine(_alphabet, numrotors, pawls, allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /**
     * Return a rotor, reading its description from _config.
     */
    public Rotor readRotor() {
        try {
            String name = this._config.next().trim();
            String type = this._config.next().trim();
            String cycle = "";
            if (!this._config.hasNext("[(].+[)]")) {
                throw new EnigmaException("Faulty Cycle!");
            }
            while (this._config.hasNext("[(].+[)]")) {
                cycle = cycle + " " + this._config.next().trim();
            }
            Permutation permutation = new Permutation(cycle, this._alphabet);
            if (type.charAt(0) == 'M') {
                String notches = type.substring(1);
                MovingRotor movingRotor =
                        new MovingRotor(name, permutation, notches);
                return movingRotor;
            } else if (type.charAt(0) == 'N') {
                FixedRotor fixedRotor = new FixedRotor(name, permutation);
                return fixedRotor;
            } else {
                Reflector reflector = new Reflector(name, permutation);
                return reflector;
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }


    /**
     * Set M according to the specification given on SETTINGS,
     * which must have the format specified in the assignment.
     */
    private void setUp(Machine M, String settings) {
        M.setRotors(settings);
    }

    /**
     * Print MSG in groups of five (except that the last group may
     * have fewer letters).
     */
    private void printMessageLine(String msg) {
        msg = msg.replaceAll(" ", "");
        while (msg.length() >= 5) {
            _output.print(msg.substring(0, 5) + " ");
            msg = msg.substring(5);
        }
        if (this._input.hasNextLine()) {
            _output.println(msg);
        } else {
            _output.print(msg);
        }
    }


    /**
     * Alphabet used in this machine.
     */
    private Alphabet _alphabet;

    /**
     * Source of input messages.
     */
    private Scanner _input;

    /**
     * Source of machine configuration.
     */
    private Scanner _config;

    /**
     * File for encoded/decoded messages.
     */
    private PrintStream _output;
}




