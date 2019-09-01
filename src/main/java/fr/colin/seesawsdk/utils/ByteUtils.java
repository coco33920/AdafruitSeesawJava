package fr.colin.seesawsdk.utils;

import java.nio.ByteBuffer;

public class ByteUtils {
    private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);


    public static byte[] intToByteArray(int data) {

        byte[] result = new byte[4];

        result[0] = (byte) ((data & 0xFF000000) >> 24);
        result[1] = (byte) ((data & 0x00FF0000) >> 16);
        result[2] = (byte) ((data & 0x0000FF00) >> 8);
        result[3] = (byte) ((data & 0x000000FF) >> 0);

        return result;
    }

    public static int fromByteArray(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF) << 0);
    }
    public static short readShort(byte[] data, int offset) {
        return (short) (((data[offset] << 8)) | ((data[offset + 1] & 0xff)));
    }

    public static byte[] shortToByteArray(short s) {
        return new byte[] { (byte) ((s & 0xFF00) >> 8), (byte) (s & 0x00FF) };
    }

    public static byte[] pinToAdress(int register, int... pins) {
        PinUtils p = new PinUtils();
        for (int i : pins) {
            p.add(i);
        }
        int pin = p.build();

        byte[] b = new byte[5];
        b[0] = (byte) register;
        byte[] bs = ByteUtils.intToByteArray(pin);
        b[1] = bs[0];
        b[2] = bs[1];
        b[3] = bs[2];
        b[4] = bs[3];

        return b;
    }


}