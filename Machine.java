package enigma;

import java.util.Collection;

/**
 * Class that represents a complete enigma machine.
 *
 * @author Shreyansh Loharuka
 */
class Machine {

    /**
     * A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     * and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     * available rotors.
     */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _allRotors = allRotors;
        _numrotors = numRotors;
        _pawls = pawls;
    }

    /**
     * Return Collection of all rotors.
     */
    Collection<Rotor> allRotors() {
        return this._allRotors;
    }

    /**
     * Return the number of rotor slots I have.
     */
    int numRotors() {
        return this._numrotors;
    }

    /**
     * Return the number pawls (and thus rotating rotors) I have.
     */
    int numPawls() {
        return this._pawls;
    }

    /**
     * Set my rotor slots to the rotors named ROTORS from my set of
     * available rotors (ROTORS[0] names the reflector).
     * Initially, all rotors are set at their 0 setting.
     */
    void insertRotors(String[] rotors) {
        if (this.containsDuplicates(rotors)) {
            throw new EnigmaException("Duplicate rotors found in setting!");
        }
        this._rotorSlots = new Rotor[rotors.length];
        boolean found = false;
        for (int i = 0; i < rotors.length; i++) {
            for (Rotor rotor : _allRotors) {
                if (rotor.name().equals(rotors[i])) {
                    if (i == 0 && !rotor.reflecting()) {
                        throw new EnigmaException("The leftmost"
                                + " rotor must be a reflecting rotor!");
                    }
                    _rotorSlots[i] = rotor;
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new EnigmaException("Rotor " + rotors[i]
                        + " Does not exist");
            }
            found = false;
        }
    }

    /**
     * Returns whether ARR contains duplicate elements.
     */
    private boolean containsDuplicates(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i != j && arr[i].equals(arr[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Set my rotors according to SETTING, which must be a string of
     * numRotors()-1 characters in my alphabet. The first letter refers
     * to the leftmost rotor setting (not counting the reflector).
     */
    void setRotors(String setting) {
        if (setting.length() < numRotors() - 1) {
            throw new EnigmaException("Setting length too "
                    + "short for number of rotors!");
        }
        char[] settingArray = setting.toCharArray();
        for (int i = 1; i < _rotorSlots.length; i++) {
            char setTo = settingArray[i - 1];
            if (!this._alphabet.chars().contains(Character.toString(setTo))) {
                throw new EnigmaException("One of the setting"
                        + " letters of the rotors is invalid!");
            }
            _rotorSlots[i].set(setTo);
        }
    }

    /**
     * Set the plugboard to PLUGBOARD.
     */
    void setPlugboard(Permutation plugboard) {
        this._plugboard = plugboard;
    }

    /**
     * Returns the result of converting the input character C (as an
     * index in the range 0..alphabet size - 1), after first advancing
     * <p>
     * the machine.
     */
    int convert(int c) {
        for (int i = _rotorSlots.length - 1; i > 0; i--) {
            if (_rotorSlots[i].atNotch()) {
                if (!_rotorSlots[i - 1].rotated()
                        && _rotorSlots[i - 1].rotates()) {
                    _rotorSlots[i - 1].setNew(_rotorSlots[i - 1].setting() + 1);
                    _rotorSlots[i - 1].setRotated(true);
                    if (!_rotorSlots[i].rotated() && _rotorSlots[i].rotates()) {
                        _rotorSlots[i].setNew(_rotorSlots[i].setting() + 1);
                        _rotorSlots[i].setRotated(true);

                    }
                }
            }
        }
        if (!_rotorSlots[_rotorSlots.length - 1].rotated()) {
            Rotor fast = _rotorSlots[_rotorSlots.length - 1];
            fast.advance();
        }
        for (Rotor rotor : _rotorSlots) {
            if (rotor.rotated()) {
                rotor.set(rotor.newSetting());
            }
            rotor.setRotated(false);
        }

        if (this._plugboard != null) {
            c = _plugboard.permute(c);
        }

        for (int i = _rotorSlots.length - 1; i >= 0; i--) {
            c = _rotorSlots[i].convertForward(c);
        }

        for (int i = 1; i < _rotorSlots.length; i++) {
            c = _rotorSlots[i].convertBackward(c);
        }
        if (this._plugboard != null) {
            c = _plugboard.invert(c);
        }
        return c;
    }


    /**
     * Returns the encoding/decoding of MSG, updating the state of
     * the rotors accordingly.
     */
    String convert(String msg) {
        char[] msgarr = msg.toCharArray();
        for (int i = 0; i < msgarr.length; i++) {
            if (msgarr[i] != ' ') {
                if (!this._alphabet.contains(msgarr[i])) {
                    throw new EnigmaException("Message Character"
                            + " not in alphabet!");
                }
                int val = this._alphabet.toInt(msgarr[i]);
                val = this.convert(val);
                msgarr[i] = this._alphabet.toChar(val);

            }
        }
        return new String(msgarr);
    }

    /**
     * Return an array of My rotors.
     */
    Rotor[] rotorSlots() {
        return this._rotorSlots;
    }

    /**
     * Set my rings according to RING.
     */
    void setOffset(String ring) {
        for (int i = 1; i < rotorSlots().length; i++) {
            char ch = ring.charAt(i - 1);
            rotorSlots()[i].setOffSet(this._alphabet.toInt(ch));
        }
    }

    /**
     * Common alphabet of my rotors.
     */
    private final Alphabet _alphabet;

    /**
     * Number of rotors in this enigma machine.
     */
    private int _numrotors;

    /**
     * All the rotors.
     */
    private Collection<Rotor> _allRotors;

    /**
     * Number of Pawls.
     */
    private int _pawls;

    /**
     * My rotor slots.
     */
    private Rotor[] _rotorSlots;

    /**
     * My plugboard permutations.
     */
    private Permutation _plugboard;
}
