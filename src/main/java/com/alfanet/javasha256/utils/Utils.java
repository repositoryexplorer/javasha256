package com.alfanet.javasha256.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {

    public static boolean isFile(String path) {
        return path != null ? Path.of(path).toFile().isFile() : false;
    }


    public static String hashToString(int[] hashWords) {
        String result = "";
        for (int i = 0; i < hashWords.length; i++) {
            result += String.format("%08x", hashWords[i]);
        }
        return result;
    }

    public static String bytesToString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            result += String.format("%02x", bytes[i]);
        }
        return result;
    }

    public static void writeToFile(String hash, String outputFilePath) {
        try {
            FileWriter fw = new FileWriter(outputFilePath);
            fw.write(hash);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] intArrayToByteArra(int[] intArray) {
        byte[] result = new byte[intArray.length * 4];
        for (int i = 0; i < intArray.length; i++) {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.putInt(intArray[i]);
            buffer.order(ByteOrder.BIG_ENDIAN);
            System.arraycopy(buffer.array(), 0, result, i * 4, 4);
        }
        return result;
    }

    public static byte[] listToArrayOfBytes(List<byte[]> arraysOfCalcMacs) {
        byte[] result = {};
        for (int i = 0; i < arraysOfCalcMacs.size(); i++) {
            byte[] bytes = arraysOfCalcMacs.get(i);
            ByteBuffer buffer = ByteBuffer.allocate(result.length + bytes.length);
            buffer.put(result);
            buffer.put(bytes);
            result = buffer.array();
        }
        return result;
    }

    public static boolean isValidHexNum(String hexNum) {
        return hexNum != null && Pattern.matches("^0x[0-9a-fA-F]+", hexNum);
    }
}
