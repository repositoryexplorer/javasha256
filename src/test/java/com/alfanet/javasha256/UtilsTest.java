package com.alfanet.javasha256;

import com.alfanet.javasha256.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {
    @Test
    public void testIntArrayToByteArray() {
        int[] input = {0x01020304, 0x05060708, 0x090A0B0C};
        byte[] result = Utils.intArrayToByteArra(input);
        byte[] testValue = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C};
        assertArrayEquals(result, testValue);
    }

    @Test
    public void testListToByteArray() {
        List<byte[]> list = new ArrayList<>();
        byte[] a = {1, 2, 3};
        byte[] b = {4, 5, 6, 7};
        byte[] c = {8, 9, 10, 11, 12};
        list.add(a);
        list.add(b);
        list.add(c);
        byte[] testArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        byte[] result = Utils.listToArrayOfBytes(list);
        assertArrayEquals(testArray, result);

    }

    @Test
    public void testIsValidHexNumber() {
        boolean res = Utils.isValidHexNum("0xab334afAB");
        assertTrue(res);
        res = Utils.isValidHexNum("0bcdef");
        assertFalse(res);
        res = Utils.isValidHexNum(" 0x34");
        assertFalse(res);
    }
}
