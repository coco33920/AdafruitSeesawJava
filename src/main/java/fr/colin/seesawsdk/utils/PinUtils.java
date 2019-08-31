package fr.colin.seesawsdk.utils;

import java.util.ArrayList;
import java.util.BitSet;

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
