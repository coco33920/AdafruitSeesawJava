package fr.charlotte.seesawsdk.utils;

import java.util.BitSet;

/**
 * Simple class to map the pin number to a 4 bytes int representation to GPIO manipulation
 */
public class PinUtils {

    BitSet end;

    public PinUtils() {
        end = new BitSet(32);
        end.set(1, 31, false);
    }

    public PinUtils add(int i) {
        end.set(i);
        return this;
    }

    public int build() {
        return (int) end.toLongArray()[0];
    }


}
