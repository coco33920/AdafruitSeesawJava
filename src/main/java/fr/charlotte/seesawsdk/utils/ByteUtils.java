package fr.charlotte.seesawsdk.utils;

/**
 * Utils class to byte manipulation
 */
public class ByteUtils {

    /**
     * Transform an int into a byte array
     * @param data The int to transform
     * @return The corresponding byte array
     */
    public static byte[] intToByteArray(int data) {

        byte[] result = new byte[4];

        result[0] = (byte) ((data & 0xFF000000) >> 24);
        result[1] = (byte) ((data & 0x00FF0000) >> 16);
        result[2] = (byte) ((data & 0x0000FF00) >> 8);
        result[3] = (byte) ((data & 0x000000FF));

        return result;
    }

    /**
     * Transform a byte array into an int
     * @param bytes The byte array to transform
     * @return The corresponding int
     */
    public static int fromByteArray(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF) << 0);
    }

    /**
     * Transform a byte array into a short
     * @param data The byte array to transform
     * @param offset the offset into the byte array
     * @return The corresponding short
     */
    public static short readShort(byte[] data, int offset) {
        return (short) (((data[offset] << 8)) | ((data[offset + 1] & 0xff)));
    }

    /**
     * Transform a short into a byte array
     * @param s The short to transform
     * @return The corresponding byte array
     */
    public static byte[] shortToByteArray(short s) {
        return new byte[] { (byte) ((s & 0xFF00) >> 8), (byte) (s & 0x00FF) };
    }

    /**
     * Method to map the pins, and address into a byte array ( GPIO Module use )
     * @param register The inner address of the function
     * @param pins The pins to set
     * @return The byte array ready to write
     */
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