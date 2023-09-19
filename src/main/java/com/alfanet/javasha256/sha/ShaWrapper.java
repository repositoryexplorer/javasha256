package com.alfanet.javasha256.sha;

import com.alfanet.javasha256.utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Optional;

public class ShaWrapper {
    public static void calculateFileHash(String path, Optional<String> outputFilePath) throws IOException {
        try (FileInputStream inStream = new FileInputStream(path)) {
            byte[] allBytes = getPadding(inStream.readAllBytes());
            String hash = calculateHash(allBytes);
            if (outputFilePath.isPresent()) {
                Utils.writeToFile(hash, outputFilePath.get());
            } else {
                System.out.println("Hash:");
                System.out.println(hash);
            }
        }
    }

    public static String calculateHash(byte[] bytes) {
        ShaUtils sha = new ShaUtils();
        int blockCount = bytes.length / 64;
        for (int i = 0; i < blockCount; i++) {
            int[] blockWords = toBlockWords(bytes, i);
            sha.calculateSha256(blockWords);
        }
        return Utils.hashToString(sha.getHasValues());
    }

    public static byte[] calculateHashAsBytes(byte[] bytes) {
        ShaUtils sha = new ShaUtils();
        int blockCount = bytes.length / 64;
        for (int i = 0; i < blockCount; i++) {
            int[] blockWords = toBlockWords(bytes, i);
            sha.calculateSha256(blockWords);
        }

        return Utils.intArrayToByteArra(sha.getHasValues());
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
}
