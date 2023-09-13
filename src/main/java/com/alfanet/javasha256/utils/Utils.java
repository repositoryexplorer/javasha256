package com.alfanet.javasha256.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;

public class Utils {

    public static boolean isFile(String path) {
        return Path.of(path).toFile().isFile();
    }

    public static byte[] getPadding2(byte[] data, int readLen) throws IOException {
        long dataLenBytes = data.length;
        int zerosLength = (int) (56 - ((readLen + 1) % 56));
        byte[] result = new byte[data.length + 9 + zerosLength];
        System.arraycopy(data, 0, result, 0, data.length);
        int idx = (int) dataLenBytes;
        result[idx++] = (byte) 0x80;
        for (byte i = 0; i < zerosLength; i++) result[idx++] = (byte) 0;
        long l = dataLenBytes * 8L;
        for (byte j = 7; j >= 0; j--) {
            result[idx + j] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    public static int intFromArray(byte[] data, byte index) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(data, index * 4, 4);
        buffer.position(0);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getInt();
    }

    public static void printHex(byte[] bytes) {
        String res = "";
        for (int i = 0; i < bytes.length; i++) {
            res += String.format("%02x", bytes[i]);
        }
        System.out.println(res);
    }
}
