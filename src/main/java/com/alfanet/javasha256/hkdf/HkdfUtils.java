package com.alfanet.javasha256.hkdf;

import com.alfanet.javasha256.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.alfanet.javasha256.hkdf.HmacUtils.sha256BytesLen;

public class HkdfUtils {


    public byte[] calculateKey(byte[] key, byte[] salt, int length, byte[] info) {
        byte[] PRK = extract(key, salt);
        System.out.println("PRK: " + Utils.bytesToString(PRK));
        return expand(PRK, length, info);
    }

    public byte[] extract(byte[] key, byte[] salt) {
        byte[] PRK = {};
        byte[] inputBytes = new byte[0];
        try {
            PRK = HmacUtils.calculateHMAC(salt, key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return PRK;
    }

    public byte[] expand(byte[] PRK, int keyLength, byte[] info) {
        int N = (int) Math.ceil((double) keyLength / (double) sha256BytesLen);
        byte[] lastT = {};
        List<byte[]> arraysOfCalcMacs = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            byte[] data = calculateExpandData(lastT, info, (byte) i);
            try {
                lastT = HmacUtils.calculateHMAC(PRK, data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            arraysOfCalcMacs.add(lastT);
        }
        return Arrays.copyOfRange(Utils.listToArrayOfBytes(arraysOfCalcMacs), 0, keyLength);
    }

    private byte[] calculateExpandData(byte[] data, byte[] info, byte counter) {
        byte[] result = new byte[data.length + info.length + 1];
        System.arraycopy(data, 0, result, 0, data.length);
        System.arraycopy(info, 0, result, data.length, info.length);
        result[result.length - 1] = counter;
        return result;
    }
}
