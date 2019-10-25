package enigma;

/**
 * Superclass that represents a rotor in the enigma machine.
 *
 * @author Shreyansh Loharuka
 */
class Rotor {

    /**
     * A rotor named NAME whose permutation is given by PERM.
     */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
        _setting = 0;
        _rotated = false;
        _newSetting = _setting;
        _offset = 0;
    }

    /**
     * Return my name.
     */
    String name() {
        return _name;
    }

    /**
     * Return my alphabet.
     */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /**
     * Return my permutation.
     */
    Permutation permutation() {
        return _permutation;
    }

    /**
     * Return the size of my alphabet.
     */
    int size() {
        return _permutation.size();
    }

    /**
     * Return true iff I have a ratchet and can move.
     */
    boolean rotates() {
        return false;
    }

    /**
     * Return true iff I reflect.
     */
    boolean reflecting() {
        return false;
    }

    /**
     * Return my current setting.
     */
    int setting() {
        return this._setting;
    }

    /**
     * Return my current setting.
     */
    int newSetting() {
        return this._newSetting;
    }

    /**
     * Return if I have been rotated.
     */
    boolean rotated() {
        return this._rotated;
    }

    /**
     * Set setting() to POSN.
     */
    void set(int posn) {
        this._setting = posn;
    }

    /**
     * Set newSetting() to POSN.
     */
    void setNew(int posn) {
        this._newSetting = posn;
    }


    /**
     * Set rotated() to FLAG.
     */
    void setRotated(boolean flag) {
        this._rotated = flag;
    }

    /**
     * Set setting() to character CPOSN.
     */
    void set(char cposn) {
        this._setting = this._permutation.alphabet().toInt(cposn);
    }

    /**
     * Return My ring setting.
     */
    int offset() {
        return _offset;
    }

    /**
     * Set my ring settings to POSN.
     */
    void setOffSet(int posn) {
        this._offset = posn;
    }

    /**
     * Return the conversion of P (an integer in the range 0..size()-1)
     * according to my permutation.
     */
    int convertForward(int p) {
        int x = this.permutation().wrap(setting() - offset());
        int contactEntered = this.permutation().wrap(p + x);
        int permutated = this._permutation.permute(contactEntered);
        x = this.permutation().wrap(setting() - offset());
        int contactExited = this.permutation().wrap(permutated - x);
        return contactExited;
    }

    /**
     * Return the conversion of E (an integer in the range 0..size()-1)
     * according to the inverse of my permutation.
     */
    int convertBackward(int e) {
        int x = this.permutation().wrap(setting() - offset());
        int contactEntered = this.permutation().wrap(e + x);
        int permutated = this._permutation.invert(contactEntered);
        x = this.permutation().wrap(setting() - offset());
        int contactExited = this.permutation().wrap(permutated - x);
        return contactExited;
    }

    /**
     * Returns true iff I am positioned to allow the rotor to my left
     * to advance.
     */
    boolean atNotch() {
        return false;
    }

    /**
     * Advance me one position, if possible. By default, does nothing.
     */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }


    /**
     * My name.
     */
    private final String _name;

    /**
     * My Ring setting.
     */
    private int _offset;

    /**
     * The permutation implemented by this rotor in its 0 position.
     */
    private Permutation _permutation;

    /**
     * The setting of this rotor.
     */
    private int _setting;

    /**
     * The setting I Advance to.
     */
    private int _newSetting;

    /**
     * Whether i have been rotated for the given character.
     */
    private boolean _rotated;


}
