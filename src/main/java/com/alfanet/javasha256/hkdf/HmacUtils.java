package com.alfanet.javasha256.hkdf;

import com.alfanet.javasha256.sha.ShaWrapper;

import java.io.IOException;
import java.util.Arrays;

public class HmacUtils {
    public static final int blockSize = 64; // block size in bytes,  always 64

    public static final int sha256BytesLen = 32; // length of SHA256 output in bytes, 32 bytes

    public static byte[] calculateHMAC(byte[] key, byte[] text) throws IOException {
        byte[] normalizedKey = normalizeKey(key);
        byte[] keyXoredByipad = xorBytes(normalizedKey, (byte) 0x36);
        byte[] keyXoredByopad = xorBytes(normalizedKey, (byte) 0x5C);
        byte[] bytesForInternalHash = new byte[text.length + keyXoredByipad.length];
        System.arraycopy(keyXoredByipad, 0, bytesForInternalHash, 0, keyXoredByipad.length);
        System.arraycopy(text, 0, bytesForInternalHash, keyXoredByipad.length, text.length);
        byte[] paddedBytesForInternalHash = ShaWrapper.getPadding(bytesForInternalHash);
        byte[] internalHash = ShaWrapper.calculateHashAsBytes(paddedBytesForInternalHash);

        byte[] bytesForResultHash = new byte[internalHash.length + keyXoredByopad.length];
        System.arraycopy(keyXoredByopad, 0, bytesForResultHash, 0, keyXoredByopad.length);
        System.arraycopy(internalHash, 0, bytesForResultHash, keyXoredByopad.length, internalHash.length);
        byte[] paddedBytesForResultHash = ShaWrapper.getPadding(bytesForResultHash);
        byte[] resultHash = ShaWrapper.calculateHashAsBytes(paddedBytesForResultHash);
        return resultHash;
    }

    private static byte[] xorBytes(byte[] inputBytes, byte value) {
        byte[] result = new byte[inputBytes.length];
        for (int i = 0; i < inputBytes.length; i++) {
            result[i] = (byte) (inputBytes[i] ^ value);
        }
        return result;
    }

    /**
     * Return normalized B-bytes length key,
     * if input key is lower than B-bytes it is filled with zeros to be B length,
     * if input key is bigger than B length, first we calculate Hash function on it,
     * and we take the result of the Hash and fill it with zeros to be B-bytes length
     *
     * @param key
     * @return
     */
    private static byte[] normalizeKey(byte[] key) {
        byte[] result = new byte[blockSize];
        Arrays.fill(result, (byte) 0x0);
        if (key.length <= blockSize) {
            System.arraycopy(key, 0, result, 0, key.length);
        } else {
            try {
                byte[] hashReadyKey = ShaWrapper.getPadding(key);
                byte[] hash = ShaWrapper.calculateHashAsBytes(hashReadyKey);
                System.arraycopy(hash, 0, result, 0, hash.length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return result;
    }
}
