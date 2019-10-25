package enigma;

/**
 * Represents a permutation of a range of integers starting at 0 corresponding
 * to the characters of an alphabet.
 *
 * @author Shreyansh Loharuka
 */
class Permutation {

    /**
     * Set this Permutation to that specified by CYCLES, a string in the
     * form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     * is interpreted as a permutation in cycle notation.  Characters in the
     * alphabet that are not included in any cycle map to themselves.
     * Whitespace is ignored.
     */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles;
    }

    /**
     * Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     * c0c1...cm.
     */
    private void addCycle(String cycle) {
        this._cycles = this._cycles + " " + cycle;
    }

    /**
     * Return the value of P modulo the size of this permutation.
     */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /**
     * Returns the size of the alphabet I permute.
     */
    int size() {
        return this._alphabet.size();
    }

    /**
     * Return the result of applying this permutation to P modulo the
     * alphabet size.
     */
    int permute(int p) {
        return this._alphabet.toInt(this.permute(this._alphabet.toChar(p)));
    }

    /**
     * Return the result of applying the inverse of this permutation
     * to  C modulo the alphabet size.
     */
    int invert(int c) {
        return this._alphabet.toInt(this.invert(this._alphabet.toChar(c)));
    }

    /**
     * Return the result of applying this permutation to the index of P
     * in ALPHABET, and converting the result to a character of ALPHABET.
     */
    char permute(char p) {
        if (this._cycles.equals("")) {
            return p;
        }
        String[] seperateCycles = this._cycles.split("[)]\\s*");
        this.formatCycle(seperateCycles);
        for (String string : seperateCycles) {
            this.cleanCycle(string);
        }
        for (String cycle : seperateCycles) {
            for (int i = 1; i <= cycle.length() - 2; i++) {
                if (cycle.length() == 3 && cycle.charAt(i) == p) {
                    return p;
                } else {
                    if (cycle.charAt(i) == p && i < cycle.length() - 2) {
                        return cycle.charAt(i + 1);
                    } else if (cycle.charAt(i) == p
                            && i == cycle.length() - 2) {
                        return cycle.charAt(1);
                    }
                }

            }
        }
        return p;
    }

    /**
     * Return the result of applying the inverse of this permutation to C.
     */
    char invert(char c) {
        if (this._cycles.equals("")) {
            return c;
        }
        String[] seperateCycles = this._cycles.split("[)]\\s*");
        this.formatCycle(seperateCycles);
        for (String string : seperateCycles) {
            this.cleanCycle(string);
        }
        for (String cycle : seperateCycles) {
            for (int i = cycle.length() - 2; i > 0; i--) {
                if (cycle.length() == 3) {
                    if (cycle.contains(Character.toString(c))) {
                        return c;
                    }
                } else {
                    if (cycle.charAt(i) == c && i != 1) {
                        return cycle.charAt(i - 1);
                    } else if (cycle.charAt(i) == c && i == 1) {
                        return cycle.charAt(cycle.length() - 2);
                    }
                }
            }
        }
        return c;
    }

    /**
     * Format CYCLE array and Return it.
     */
    String[] formatCycle(String[] cycle) {
        for (int i = 0; i < cycle.length; i++) {
            if (cycle[i].equals("")) {
                throw new EnigmaException("Malformed Cycle!");
            }
            cycle[i] = cycle[i].trim() + ")";
        }

        for (int i = 0; i < cycle.length; i++) {
            String current = cycle[i];
            for (int j = 1; j < current.length() - 1; j++) {
                if (!this._alphabet.contains(current.charAt(j))) {
                    throw new EnigmaException("Malformed Cycle!");
                }
            }
        }
        return cycle;
    }

    /**
     * Clean up the CYCLE and return it.
     */
    String cleanCycle(String cycle) {
        cycle = cycle.replaceAll("\\s", "");
        for (int i = 0; i < cycle.length(); i++) {
            if (cycle.charAt(i) == '(' || cycle.charAt(i) == ')') {
                continue;
            }
            char check = cycle.charAt(i);
            if (!this._alphabet.chars().contains(Character.toString(check))) {
                throw new EnigmaException("Cycle has invalid character!");
            }
        }
        return cycle;
    }

    /**
     * Return the alphabet used to initialize this Permutation.
     */
    Alphabet alphabet() {
        return _alphabet;
    }

    /**
     * Return true iff this permutation is a derangement (i.e., a
     * permutation for which no value maps to itself).
     */
    boolean derangement() {
        String[] seperateCycles = this._cycles.split("\\)[\\s+]?\\(");
        for (String cycle : seperateCycles) {
            if (cycle.length() == 3) {
                return false;
            }
        }
        return true;
    }

    /**
     * Alphabet of this permutation.
     */
    private Alphabet _alphabet;

    /**
     * Stores the cycles for this permutation.
     */
    private String _cycles;

}
