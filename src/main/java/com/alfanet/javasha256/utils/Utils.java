package com.alfanet.javasha256.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;

public class Utils {

    public static boolean isFile(String path) {
        return Path.of(path).toFile().isFile();
    }

    public static byte[] getPadding(byte[] data) throws IOException {
        int lastBlockLen = data.length % 64;
        int zerosLength = (int) (64 - ((lastBlockLen + 9) % 64));
        byte[] result = new byte[data.length + 9 + zerosLength];
        System.arraycopy(data, 0, result, 0, data.length);
        int idx = (int) data.length;
        result[idx++] = (byte) 0x80;
        for (byte i = 0; i < zerosLength; i++) result[idx++] = (byte) 0;
        long l = data.length * 8L;
        for (byte j = 7; j >= 0; j--) {
            result[idx + j] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    public static int[] toBlockWords(byte[] data, int index) {
        int[] result = new int[16];
        for (int j = 0; j < 16; j++) {
            byte[] intBytes = new byte[4];
            System.arraycopy(data, (index * 64) + (j * 4), intBytes, 0, 4);
            ByteBuffer buffer = ByteBuffer.wrap(intBytes);
            buffer.order(ByteOrder.BIG_ENDIAN);
            result[j] = buffer.getInt();
        }
        return result;
    }

    public static String hashToString(int[] hashWords) {
        String result = "";
        for (int i = 0; i < hashWords.length; i++) {
            result += String.format("%08x", hashWords[i]);
        }
        return result;
    }
}
