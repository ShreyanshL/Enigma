package enigma;

/**
 * Class that represents a rotating rotor in the enigma machine.
 *
 * @author Shreyansh Loharuka
 */
class MovingRotor extends Rotor {

    /**
     * A rotor named NAME whose permutation in its default setting is
     * PERM, and whose notches are at the positions indicated in NOTCHES.
     * The Rotor is initally in its 0 setting (first character of its
     * alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
    }

    @Override
    boolean atNotch() {
        char[] arr = this.notches().toCharArray();
        for (char c : arr) {
            int curr = this.permutation().wrap(this.setting());
            if (curr == this.alphabet().toInt(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return a String containing my notches.
     */
    String notches() {
        return this._notches;
    }

    @Override
    void advance() {
        this.set(this.permutation().wrap(this.setting() + 1));
    }

    @Override
    boolean rotates() {
        return true;
    }

    /**
     * String containing my notches.
     */
    private String _notches;

}
